package tk.faidnawa.latihanandroidcrud.data;

/**
 * Created by faidl on 25/03/2017.
 */

public class Data {
    private String id, nama, nim;

    public Data() {
    }

    public Data(String id, String nama, String nim) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getnim() {
        return nim;
    }

    public void setnim(String nim) {
        this.nim = nim;
    }
}
