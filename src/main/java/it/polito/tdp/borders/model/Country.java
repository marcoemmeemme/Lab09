package it.polito.tdp.borders.model;

public class Country 
{
	private String StateAbb;
	private int CCode;
	private String StateNme;
	private int statiConfinanti;
	public Country(String stateAbb, int cCode, String stateNme) {
		super();
		StateAbb = stateAbb;
		CCode = cCode;
		StateNme = stateNme;
		this.setStatiConfinanti(statiConfinanti);
	}
	public String getStateAbb() {
		return StateAbb;
	}
	public void setStateAbb(String stateAbb) {
		StateAbb = stateAbb;
	}
	public int getCCode() {
		return CCode;
	}
	public void setCCode(int cCode) {
		CCode = cCode;
	}
	public String getStateNme() {
		return StateNme;
	}
	public void setStateNme(String stateNme) {
		StateNme = stateNme;
	}
	public int getStatiConfinanti() {
		return statiConfinanti;
	}
	public void setStatiConfinanti(int statiConfinanti) {
		this.statiConfinanti = statiConfinanti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CCode;
		result = prime * result + ((StateAbb == null) ? 0 : StateAbb.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (CCode != other.CCode)
			return false;
		if (StateAbb == null) {
			if (other.StateAbb != null)
				return false;
		} else if (!StateAbb.equals(other.StateAbb))
			return false;
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.StateNme+" "+"("+this.StateAbb+")";
	}
	
	
	
}
