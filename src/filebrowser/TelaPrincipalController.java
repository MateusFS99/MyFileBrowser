package filebrowser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TelaPrincipalController implements Initializable {
    
    @FXML
    private TextField tfFiltro;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btVoltar;
    @FXML
    private Button btAtualizar;
    @FXML
    private Button btNovaPasta;
    @FXML
    private Button btFechar;
    @FXML
    private TableView<Arquivo> tabela;
    @FXML
    private TableColumn<Arquivo, String> colNome;
    @FXML
    private TableColumn<Arquivo, Long> colTamanho;
    @FXML
    private TableColumn<Arquivo, ImageView> colPasta;
    
    @FXML
    private MenuItem btExcluir;
    
    
    private int TL = 0;
    private List <String> pastainicial = new ArrayList();
    private File pasta = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        colPasta.setCellValueFactory(new PropertyValueFactory<>("icon"));
        pastainicial.add("C:\\");
        preencherTabela();
    }    
    
    private void preencherTabela(){
        
        pasta = new File(pastainicial.get(TL));
        Arquivo arq;
        List <Arquivo> arquivos = new ArrayList();
        
        tabela.setRowFactory(tv -> new TableRow<Arquivo>() {
            
                @Override
                public void updateItem(Arquivo item, boolean empty) {
                
                    super.updateItem(item, empty);
                    if (item != null && item.isPasta()) 
                        setStyle("-fx-background-color: yellow;");
                    else 
                        setStyle("-fx-background-color: white;");   
                }
            });
        for (int i = 0; i < pasta.listFiles().length; i++){
            
            if(pasta.listFiles()[i].isDirectory())
                arq = new Arquivo(pasta.listFiles()[i].getName(),
                pasta.listFiles()[i].length(),
                pasta.listFiles()[i].isDirectory(),
                new ImageView(new Image("icons/folder.png")));
            else
                arq = new Arquivo(pasta.listFiles()[i].getName(),
                pasta.listFiles()[i].length(),
                pasta.listFiles()[i].isDirectory(),
                new ImageView(new Image("icons/file.png")));
            
            if(!"".equals(pasta.listFiles()[i].getAbsolutePath()))
                if(pasta.listFiles()[i].getAbsolutePath().endsWith(tfFiltro.getText()))
                    arquivos.add(arq);
            
        }
        tabela.setItems(FXCollections.observableArrayList(arquivos));
    }

    @FXML
    private void evtAtualizar(ActionEvent event) {
        
        preencherTabela();
    }

    @FXML
    private void evtNovapasta(ActionEvent event) {
        
        String novapasta;
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Informe o nome da pasta");
        Optional <String> res = dialog.showAndWait();
        if(res != null){
            
            File npasta = new File(pastainicial.get(TL) + "//" + res.get());
            npasta.mkdir();
            preencherTabela();
        }
    }

    @FXML
    private void evtFechar(ActionEvent event) {
        
        Platform.exit();
    }
    
    @FXML
    private void evtClick(MouseEvent event) {
        
        if(event.getClickCount() == 2) {
            
            TL++;
            pastainicial.add(pastainicial.get(TL-1) + "\\" + tabela.getSelectionModel().getSelectedItem().getNome());
            preencherTabela();
        }
    }

    @FXML
    private void evtVoltar(ActionEvent event) {
        
        if(TL > 0){
            
            pastainicial.remove(TL);
            TL--;
            preencherTabela();
        }
    }

    @FXML
    private void evtExcluir(ActionEvent event) {
        
        File arq = new File(pastainicial.get(TL) + "\\" + tabela.getSelectionModel().getSelectedItem().getNome());
        
        arq.delete();
        preencherTabela();
    }

    @FXML
    private void evtBuscar(ActionEvent event) {
        
        preencherTabela();
    }

}
