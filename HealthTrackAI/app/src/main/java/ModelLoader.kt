import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ModelLoader(private val context: Context) {
    private var interpreter: Interpreter? = null

    fun loadModel(): Interpreter? {
        try {
            // Load the model from the assets directory
            val modelFileDescriptor = context.assets.openFd("recommendation_model.tflite")
            val inputStream = FileInputStream(modelFileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = modelFileDescriptor.startOffset
            val declaredLength = modelFileDescriptor.declaredLength
            val modelByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

            // Create the interpreter with the loaded model
            interpreter = Interpreter(modelByteBuffer)
        } catch (e: IOException) {
            Log.e(TAG, "Error loading model: ${e.message}")
        }

        return interpreter
    }

    companion object {
        private const val TAG = "ModelLoader"
    }
}

class AIHealthAnalyzer(private val interpreter: Interpreter) {

    fun performAnalysis(heartRate: Double, sleepHours: Double, activityLevel: String): String {
        // Prepare input data for the model
        val inputTensor = Array(1) { FloatArray(3) } // Assuming 3 input features
        inputTensor[0][0] = heartRate.toFloat()
        inputTensor[0][1] = sleepHours.toFloat()
        inputTensor[0][2] = mapActivityLevel(activityLevel) // Map string to numerical value

        // Allocate output buffer
        val outputTensor = Array(1) { FloatArray(1) } // Assuming 1 output value

        // Run inference with the model
        interpreter.run(inputTensor, outputTensor)

        // Process the output to generate recommendations
        val recommendationValue = outputTensor[0][0]
        return processRecommendation(recommendationValue)
    }

    private fun mapActivityLevel(activityLevel: String): Float {
        // Map activity level string to numerical value
        return when (activityLevel.toLowerCase()) {
            "low" -> 0.0f
            "medium" -> 1.0f
            "high" -> 2.0f
            else -> 0.0f // Default to low if unknown
        }
    }

    private fun processRecommendation(recommendationValue: Float): String {
        // Process the recommendation value from the model output
        // Example logic: Map numerical value to recommendation message
        return when {
            recommendationValue < 0.5 -> "Recommendation: Low risk, maintain current lifestyle."
            recommendationValue < 0.8 -> "Recommendation: Moderate risk, consider lifestyle improvements."
            else -> "Recommendation: High risk, take immediate action."
        }
    }
}
