import java.io.*;
import java.net.Socket;
import com.fathzer.soft.javaluator.DoubleEvaluator;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                try {
                    double result = evaluateExpression(inputLine);
                    writer.println("Result: " + result);
                } catch (Exception e) {
                    writer.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double evaluateExpression(String expression) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(expression);
        System.out.println(expression + " = " + result);
        return result;
    }
}
