import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Paths.*;

public class ListasMain {

    private static final List<Encuesta> encuestas = new ArrayList<>(1);

    public static void main(String[] args) {
        loadData();
        tabularEncuesta();
        //tabularEncuestaFuncional();
    }

    public static void tabularEncuesta() {

        int cuentaIngrid = 0;
        int cuentaFajardo = 0;
        int cuentaFederico = 0;
        int cuentaPetro = 0;
        int cuentaRodolfo = 0;

        for (Encuesta e : encuestas) {
            switch (e.getCandidato()) {
                case "Ingrid":
                    cuentaIngrid++;
                    break;
                case "Fajardo":
                    cuentaFajardo++;
                    break;
                case "Federico":
                    cuentaFederico++;
                    break;
                case "Petro":
                    cuentaPetro++;
                    break;
                case "Rodolfo":
                    cuentaRodolfo++;
                    break;
            }
        }
        Map<String, Integer> conteoCandidatos = new HashMap<>();
        conteoCandidatos.put("Ingrid", cuentaIngrid);
        conteoCandidatos.put("Fajardo", cuentaFajardo);
        conteoCandidatos.put("Federico", cuentaFederico);
        conteoCandidatos.put("Petro", cuentaPetro);
        conteoCandidatos.put("Rodolfo", cuentaRodolfo);

        /*Map<String, Integer> conteoMap = new TreeMap<>();
        conteoMap.put("Fajardo",cuentaFajardo);
        conteoMap.put("Federico",cuentaFederico);
        conteoMap.put("Ingrid",cuentaIngrid);
        conteoMap.put("Petro",cuentaPetro);
        conteoMap.put("Rodolfo", cuentaRodolfo);
         */

        // Ordenamiento
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(conteoCandidatos.entrySet());
        lista.sort(Map.Entry.comparingByValue());
        Collections.reverse(lista);

        for (Map.Entry<String, Integer> registro : lista) {
            System.out.println("Candidato " + registro.getKey() + " votos " + registro.getValue());
        }

    }

    private static void loadData() {
        try {
            Path rootDir = get(".").normalize().toAbsolutePath();
            List<String> lines = Files.readAllLines(get(rootDir.toString() + "/src/data.txt"), StandardCharsets.UTF_8);
            for (String line : lines) {
                Encuesta encuesta = new Encuesta();
                String[] values = line.split(",");
                encuesta.setId(Integer.valueOf(values[0]));
                encuesta.setNombre(values[1]);
                encuesta.setCandidato(values[2]);
                encuestas.add(encuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tabularEncuestaFuncional() {
        encuestas
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCandidato()))
                .entrySet()
                .stream()
                .sorted(
                        Collections.reverseOrder(
                                Map.Entry.comparingByValue(
                                        Comparator.comparing(encuesta -> encuesta.size())
                                )
                        ))
                .forEach(resultado -> {
                    System.out.println("Candidato: " + resultado.getKey()
                            + ", votos: " + resultado.getValue().size());
                });
    }
}
