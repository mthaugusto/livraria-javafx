package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.AutoresDao;
import com.example.data.LivrosDao;
import com.example.model.Autores;
import com.example.model.Livros;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    // Campos dos livros
    @FXML
    TextField txtTitulo;
    @FXML
    TextField txtGenero;
    @FXML
    TextField txtPaginas;
    @FXML
    TextField txtValor;
    @FXML
    TableView<Livros> tabelaLivros;
    @FXML
    TableColumn<Livros, Integer> colId;
    @FXML
    TableColumn<Livros, String> colTitulo;
    @FXML
    TableColumn<Livros, String> colGenero;
    @FXML
    TableColumn<Livros, Integer> colPaginas;
    @FXML
    TableColumn<Livros, BigDecimal> colValor;
    @FXML TableColumn<Autores, String> colAutor;
    @FXML ComboBox<Autores> cboAutores;


    //campos do autor
    @FXML TextField txtNome;
    @FXML TextField txtEmail;
    @FXML TextField txtSite;
    @FXML TableView<Autores> tabelaAutores;
    @FXML TableColumn<Autores, Integer> colIdAutor;
    @FXML TableColumn<Autores, String> colNome;
    @FXML TableColumn<Autores, String> colEmail;
    @FXML TableColumn<Autores, String> colWebsite;

    LivrosDao livroDao = new LivrosDao();
    AutoresDao autorDao = new AutoresDao();

    // métodos do veículo
    public void cadastrarLivro() {
        var livro = new Livros(
                txtTitulo.getText(),
                txtGenero.getText(),
                Integer.valueOf(txtPaginas.getText()),
                new BigDecimal(txtValor.getText()),
                cboAutores.getSelectionModel().getSelectedItem()
            );

        try {
            livroDao.inserir(livro);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        consultarLivros();
    }

    public void consultarLivros() {
        tabelaLivros.getItems().clear();
        try {
            livroDao.buscarTodos().forEach(livro -> tabelaLivros.getItems().add(livro));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar livro. " + e.getMessage());
        }
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o item selecionado? Esta ação não pode ser desfeita.");
        var resposta = alert.showAndWait();
        return resposta.get().getButtonData().equals(ButtonData.OK_DONE);
    }

    public void apagarLivro() {
        var livro = tabelaLivros.getSelectionModel().getSelectedItem();

        if (livro == null) {
            mostrarMensagem("Erro", "Selecione um livro na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                livroDao.apagar(livro);
                tabelaLivros.getItems().remove(livro);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar livro do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void atualizarLivro(Livros livro) {
        try {
            livroDao.atualizar(livro);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados do livro");
            e.printStackTrace();
        }
    }


    //métodos do autor
    public void cadastrarAutor() {
        var autor = new Autores(
                txtNome.getText(),
                txtEmail.getText(),
                txtSite.getText()
            );

        try {
            autorDao.inserir(autor);
        } catch (SQLException erro) {
            mostrarMensagem("Erro", "Erro ao cadastrar. " + erro.getMessage());
        }

        try {
            cboAutores.getItems().clear();
            cboAutores.getItems().addAll(autorDao.buscarTodos());
        } catch (SQLException e) {
            mostrarMensagem("Err", "Erro ao carregar autores");
        }

        consultarAutores();
    }

    public void apagarAutor() {
        var autor = tabelaAutores.getSelectionModel().getSelectedItem();

        if (autor == null) {
            mostrarMensagem("Erro", "Selecione um autor na tabela para apagar");
            return;
        }

        if (confirmarExclusao()) {
            try {
                autorDao.apagar(autor);
                tabelaAutores.getItems().remove(autor);
            } catch (SQLException e) {
                mostrarMensagem("Erro", "Erro ao apagar autor do banco de dados. " + e.getMessage());
                e.printStackTrace();
            }

            try {
            cboAutores.getItems().clear();
            cboAutores.getItems().addAll(autorDao.buscarTodos());
            } catch (SQLException e) {
                mostrarMensagem("Err", "Erro ao carregar autores");
        }
        }

    }

    public void consultarAutores() {
        tabelaAutores.getItems().clear();
        try {
            autorDao.buscarTodos().forEach(autor -> tabelaAutores.getItems().add(autor));
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao buscar autores. " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
        colTitulo.setOnEditCommit(event -> atualizarLivro(event.getRowValue().titulo(event.getNewValue())));

        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colGenero.setCellFactory(TextFieldTableCell.forTableColumn());
        colGenero.setOnEditCommit(event -> atualizarLivro(event.getRowValue().genero(event.getNewValue())));

        colPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
        colPaginas.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colPaginas.setOnEditCommit(event -> atualizarLivro(event.getRowValue().paginas(event.getNewValue())));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> atualizarLivro(event.getRowValue().valor(event.getNewValue())));

        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        colIdAutor.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colWebsite.setCellValueFactory(new PropertyValueFactory<>("site"));

        tabelaLivros.setEditable(true);

        try {
            cboAutores.getItems().addAll(autorDao.buscarTodos());
        } catch (SQLException e) {
            mostrarMensagem("Err", "Erro ao carregar autores");
        }

        consultarLivros();
        consultarAutores();
    }

}
