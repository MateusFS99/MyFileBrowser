package filebrowser;

import javafx.scene.image.ImageView;

public class Arquivo {
    
    private String nome;
    private long tamanho;
    private boolean pasta;
    private ImageView icon;

    public Arquivo(String nome, long tamanho, boolean pasta, ImageView icon) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.pasta = pasta;
        this.icon = icon;
    }

    public Arquivo() {
        this("",0,false,null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isPasta() {
        return pasta;
    }

    public void setPasta(boolean pasta) {
        this.pasta = pasta;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
}
