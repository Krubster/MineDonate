package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameField {

    public DataOfUIElement okButton;
    public DataOfUIElement cancelButton;
    public String title;
    public DataOfUITextHolderElement textField;

    public DataOfUIFrameField() {

        title = "-";
        okButton = new DataOfUIElement("Ok", 50, 20);
        cancelButton = new DataOfUIElement("Cancel", 50, 20);
        textField = new DataOfUITextHolderElement("", "Name", 160, 20);

    }

    public DataOfUIFrameField(String _title, DataOfUIElement _okButton, DataOfUITextHolderElement _textField) {

        this();

        title = _title;
        okButton = _okButton;
        textField = _textField;

    }

}
