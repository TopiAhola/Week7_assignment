
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML private TextField number1Field;
    @FXML private TextField number2Field;
    @FXML private Label resultLabel;

    @FXML
    private void onCalculateClick() {
        try {
            double num1 = Double.parseDouble(number1Field.getText());
            double num2 = Double.parseDouble(number2Field.getText());



            double sum = num1 + num2;
            double product = num1 * num2;
            double subtract = num1 - num2;

            if(num2 == 0){
                throw new ArithmeticException();
            }
            double divide = num1 / num2;


            resultLabel.setText("Sum: " + sum + ",\n Product: " + product + ",\n Divide: " + divide + ",\n Subtract: " + subtract );

            // Save to DB
            ResultService.saveResult(num1, num2, sum, product,  subtract, divide);

        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers!");

        } catch (ArithmeticException e) {
            resultLabel.setText("Cannot divide by zero!");
        }
    }
}