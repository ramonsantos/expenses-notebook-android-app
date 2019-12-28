package io.github.ramonsantos.expensesnotebook.util;

import android.icu.text.NumberFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mask {
    public static TextWatcher moneyMask(final EditText editText) {
        return new TextWatcher() {
            private String currentString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(currentString)) {
                    editText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    currentString = formatted.replaceAll("[R$]", "");
                    editText.setText(currentString);
                    editText.setSelection(currentString.length());

                    editText.addTextChangedListener(this);
                }
            }
        };
    }
}
