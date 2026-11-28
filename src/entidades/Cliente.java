package entidades;

public class Cliente {
	private int cliid, tipid;
	private String clinombre, cliApellidos, tipnombre;
	private char cliSexo;
	private double cliLimiteCredito;

	public Cliente() {
		this(0, "", "", 'M', 0.0, 0, "");
	}

	public Cliente(int cliid, String clinombre, String cliApellidos, char cliSexo, double cliLimiteCredito, int tipid,
			String tipnombre) {
		super();
		this.cliid = cliid;
		this.clinombre = clinombre;
		this.cliApellidos = cliApellidos;
		this.cliSexo = cliSexo;
		this.cliLimiteCredito = cliLimiteCredito;
		this.tipid = tipid;
		this.tipnombre = tipnombre;
	}

	@Override
	public String toString() {
		return cliid + ", " + clinombre + ", " + cliApellidos + ", " + cliSexo + ", " + cliLimiteCredito + ", "
				+ tipnombre;
	}

	public int getCliid() {
		return cliid;
	}

	public void setCliid(int cliid) {
		this.cliid = cliid;
	}

	public String getClinombre() {
		return clinombre;
	}

	public void setClinombre(String clinombre) {
		this.clinombre = clinombre;
	}

	public String getCliApellidos() {
		return cliApellidos;
	}

	public void setCliApellidos(String cliApellidos) {
		this.cliApellidos = cliApellidos;
	}

	public char getCliSexo() {
		return cliSexo;
	}

	public void setCliSexo(char cliSexo) {
		this.cliSexo = cliSexo;
	}

	public double getCliLimiteCredito() {
		return cliLimiteCredito;
	}

	public void setCliLimiteCredito(double cliLimiteCredito) {
		this.cliLimiteCredito = cliLimiteCredito;
	}

	public int getTipid() {
		return tipid;
	}

	public void setTipid(int tipid) {
		this.tipid = tipid;
	}

	public String getTipnombre() {
		return tipnombre;
	}

	public void setTipnombre(String tipnombre) {
		this.tipnombre = tipnombre;
	}
}