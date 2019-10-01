package gorboe.com.mindb;

public class Kontakt {

    private long _ID;
    private String navn;
    private String telefon;

    public Kontakt(long _ID, String navn, String telefon) {
        this._ID = _ID;
        this.navn = navn;
        this.telefon = telefon;
    }

    public Kontakt(String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public Kontakt() {

    }

    public long get_ID() {
        return _ID;
    }

    public String getNavn() {
        return navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
