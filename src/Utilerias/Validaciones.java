package Utilerias;

public class Validaciones {

	//Se creó ese método para la facilidad de validar si es vacío el valor
	//es caso de que esté vacío (empty) o que tenga valor nulo
	public static boolean esVacio(String texto) {
		return texto == null || texto.trim().isEmpty();
	}

	public static boolean LongitudValida(String texto, int max) {
		if (texto == null){
			return false;
		}
		return texto.trim().length() <= max;
	}

	public static boolean esEnteroPositivo(String texto) {
		if (esVacio(texto)){
			return false;
		}
		try {
			return Integer.parseInt(texto.trim()) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean esDecimalValido(String texto) {
		if (esVacio(texto)) {
			return false;
		}
		try {
			String limpio = texto.replace("$", "").replace(",", "").trim();
			return Double.parseDouble(limpio) >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean esSexoValido(String sexo) {
		if (esVacio(sexo)) {
			return false;
		}
		String s = sexo.trim().toUpperCase(); //lo hacemos mayúscula para no tener que validar en minúscula también
		return s.equals("M") || s.equals("F");
	}
}