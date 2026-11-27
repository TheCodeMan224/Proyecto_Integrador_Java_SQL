package entidades;

public class Tipo {
	private int tipid;
	private String tipnombre;

	public Tipo() {
		this(0, "");
	}

	public Tipo(int tipid, String tipnombre) {
		super();
		this.tipid = tipid;
		this.tipnombre = tipnombre;
	}

	@Override
	public String toString() {
		return tipnombre;
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