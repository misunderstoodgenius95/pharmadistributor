package pharma.config;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.GestureEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class Validations<T> {

    private final List<TextField> textFieldList;
    private final List<ChoiceBox<T>> list_choice;
    private  Button button;
    private Validations(ValidationBuilder<T> builder) {
        this.textFieldList = builder.textFieldList;
        this.list_choice = builder.list_choice;
        this.button=builder.button;
    }

    /**
     * Validates all TextFields and ChoiceBoxes.
     *
     * @return true if all validations pass, false otherwise.
     */
    public void validate() {

        // Validate TextFields
        button.addEventFilter(ActionEvent.ACTION, event -> {
            boolean result;
            if (textFieldList.isEmpty()) {

                result =list_choice.stream().anyMatch(choice -> choice.getValue() == null);
            }
            else if(list_choice.isEmpty()){
                result = textFieldList.stream().
                        anyMatch(textField -> {
                            if(textField.getText().isEmpty()){
                                return  true;
                            }else if(textField.getId()!=null) {
                                return !InputValidation.get_validation(textField.getId().split("-")[0], textField.getText());
                            }
                            return  false;

                        });


            }else {

                result = Stream.concat(list_choice.stream().map(choice-> choice.getValue()==null),
                        textFieldList.stream().
                                map(textField -> {
                                    if(textField.getText().isEmpty()){
                                        return  true;
                                    }else if(textField.getId()!=null) {
                                        return !InputValidation.get_validation(textField.getId().split("-")[0], textField.getText());
                                    }
                                    return  false;}
                                )).anyMatch(results->results);

            }
            if (result) {
                Utility.create_alert(Alert.AlertType.WARNING, "Attenzione!", " Riempire tutti campi!");
                event.consume();

            }



        });
    }

    public static class ValidationBuilder<T> {
        private final List<TextField> textFieldList;
        private final List<ChoiceBox<T>> list_choice;
        private  Button button;
        public ValidationBuilder() {
            textFieldList = new ArrayList<>();
            list_choice = new ArrayList<>();
            button=new Button();
        }
        public static ValidationBuilder getInstance(){

            return  new ValidationBuilder<>();
        }
        /**
         * Adds an email TextField with validation.
         *
         * @param textField The TextField to validate as an email.
         * @return The builder instance.
         */
        public ValidationBuilder<T> valid_email(TextField textField) {
            textField.setId("Email-" + UUID.randomUUID());
            textFieldList.add(textField);
            return this;
        }

        /**
         * Adds a password TextField with validation.
         *
         * @param textField The TextField to validate as a password.
         * @return The builder instance.
         */
        public ValidationBuilder<T> valid_password(TextField textField) {
            textField.setId("Password-" + UUID.randomUUID());
            textFieldList.add(textField);
            return this;
        }

        /**
         * Adds a ChoiceBox with validation.
         *
         * @param choiceBox The ChoiceBox to validate.
         * @return The builder instance.
         */
        public ValidationBuilder<T> valid_choice(ChoiceBox<T> choiceBox) {
            list_choice.add(choiceBox);
            return this;
        }

        /**
         * Builds the Validations object.
         *
         * @return A new Validations instance.
         */
        public Validations<T> build(Button button) {
            this.button=button;
            return new Validations<>(this);
        }
    }
}
