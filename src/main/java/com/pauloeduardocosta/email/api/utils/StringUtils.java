package com.pauloeduardocosta.email.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static int quantidadeDeOcorencias(String texto, String padrao) {
        Matcher matcher = Pattern.compile(padrao).matcher(texto);
        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        return counter;
    }
}
