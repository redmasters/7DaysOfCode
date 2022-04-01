package io.red;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        String API_KEY = "k_0qu6jfk0";
        URI apiIMDB = URI.create("https://imdb-api.com/en/API/Top250Movies/" + API_KEY);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri((apiIMDB))
            .build();

        HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println("Resposta " + json);
        String[] listaFilmes = parseFilmes(json);
    }

        public static String[] parseFilmes(String json){
            Matcher matcher = Pattern.compile(".*\\[(.*)].*").matcher(json);

            if(!matcher.matches()){
                throw new IllegalArgumentException("Padroes nao encontrados em " + json);
            }

            String[] listaFilmes = matcher.group(1).split("},\\{");
            int ultimo = listaFilmes.length - 1;
            String ultimaString = listaFilmes[ultimo];
            listaFilmes[ultimo] = ultimaString
                .substring(0, ultimaString.length() -1);
            return listaFilmes;
        }



}
