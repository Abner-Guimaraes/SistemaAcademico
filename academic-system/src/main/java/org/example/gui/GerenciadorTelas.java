package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.SistemaAcademico;
import org.example.controller.ControladorSistemaAcademico;
import org.example.model.Turma;
import org.example.model.Usuario;

public class GerenciadorTelas {
    private Stage stage;
    private ControladorSistemaAcademico controller;
    private Usuario usuarioLogado;

    public GerenciadorTelas(Stage stage) {
        this.stage = stage;
        this.controller = SistemaAcademico.getInstance().getAcademicSystemController();
    }

    public void iniciar() {
        mostrarTelaLogin();
    }

    public void mostrarTelaLogin() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("SISTEMA ACADÊMICO - LOGIN");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuário (admin/prof)");
        txtUsuario.setMaxWidth(200);

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(200);

        Button btnLogin = new Button("Entrar");
        btnLogin.setOnAction(e -> {
            try {
                this.usuarioLogado = controller.autenticar(txtUsuario.getText(), txtSenha.getText());
                mostrarMenuPrincipal();
            } catch (Exception ex) {
                mostrarAlertaErro("Erro de Login", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(lblTitulo, txtUsuario, txtSenha, btnLogin);
        stage.setScene(new Scene(vbox, 400, 300));
        stage.setTitle("Login");
    }

    public void mostrarMenuPrincipal() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        Label lblBemVindo = new Label("Bem-vindo(a), " + usuarioLogado.getRole());
        lblBemVindo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> {
            controller.logout(usuarioLogado.getUsername());
            this.usuarioLogado = null;
            mostrarTelaLogin();
        });

        HBox topBox = new HBox(20, lblBemVindo, btnLogout);
        topBox.setAlignment(Pos.CENTER_LEFT);
        pane.setTop(topBox);

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setAlignment(Pos.CENTER);

        if (usuarioLogado.getRole().equals("ADMIN")) {
            Button btnCadastrarTurma = new Button("1. Cadastrar Turma");
            btnCadastrarTurma.setOnAction(e -> mostrarTelaCadastroTurma());

            Button btnConfigPersistencia = new Button("2. Configurar Persistência");
            btnConfigPersistencia.setOnAction(e -> mostrarTelaConfigPersistencia());

            Button btnSalvarDados = new Button("3. Salvar Dados");
            btnSalvarDados.setOnAction(e -> {
                try {
                    controller.salvarDados(usuarioLogado.getUsername());
                    mostrarAlertaSucesso("Sucesso", "Dados salvos com sucesso!");
                } catch (Exception ex) {
                    mostrarAlertaErro("Erro ao Salvar", ex.getMessage());
                }
            });

            Button btnListarTurmas = new Button("4. Listar Turmas");
            btnListarTurmas.setOnAction(e -> mostrarTelaListarTurmas());

            Button btnRelatorios = new Button("5. Relatórios");
            btnRelatorios.setOnAction(e -> mostrarTelaRelatorios());

            menu.getChildren().addAll(btnCadastrarTurma, btnConfigPersistencia, btnSalvarDados, btnListarTurmas, btnRelatorios);
        } else if (usuarioLogado.getRole().equals("PROFESSOR")) {
            Button btnCadastrarAvaliacao = new Button("1. Cadastrar Avaliação");
            btnCadastrarAvaliacao.setOnAction(e -> mostrarTelaCadastroAvaliacao());

            Button btnListarTurmas = new Button("2. Listar Turmas");
            btnListarTurmas.setOnAction(e -> mostrarTelaListarTurmas());

            Button btnRelatorios = new Button("3. Relatórios");
            btnRelatorios.setOnAction(e -> mostrarTelaRelatorios());

            menu.getChildren().addAll(btnCadastrarAvaliacao, btnListarTurmas, btnRelatorios);
        }

        pane.setCenter(menu);
        stage.setScene(new Scene(pane, 500, 400));
        stage.setTitle("Menu Principal");
    }

    private void mostrarTelaCadastroTurma() {
        VBox vbox = criarLayoutBase("Cadastrar Turma");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código da Turma");
        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Título da Turma");

        Button btnSalvar = new Button("Salvar Turma");
        btnSalvar.setOnAction(e -> {
            try {
                // CORRIGIDO: Passando o Role (ADMIN) ao invés do Username
                controller.registrarTurma(txtCodigo.getText(), txtTitulo.getText(), usuarioLogado.getRole());
                mostrarAlertaSucesso("Sucesso", "Turma registrada com sucesso!");
                mostrarMenuPrincipal();
            } catch (Exception ex) {
                mostrarAlertaErro("Erro no Cadastro", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(txtCodigo, txtTitulo, btnSalvar);
        stage.setScene(new Scene(vbox, 400, 300));
    }

    private void mostrarTelaCadastroAvaliacao() {
        VBox vbox = criarLayoutBase("Cadastrar Avaliação");

        TextField txtCodigoTurma = new TextField();
        txtCodigoTurma.setPromptText("Código da Turma");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome da Avaliação");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Prova", "Trabalho Prático", "Seminário", "Atividade");
        cbTipo.setPromptText("Tipo de Avaliação");

        TextField txtValor = new TextField();
        txtValor.setPromptText("Valor");
        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso");

        Button btnSalvar = new Button("Salvar Avaliação");
        btnSalvar.setOnAction(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                double peso = Double.parseDouble(txtPeso.getText());
                // CORRIGIDO: Passando o Role ao invés do Username
                controller.registrarAvaliacao(txtCodigoTurma.getText(), txtNome.getText(), cbTipo.getValue(), valor, peso, usuarioLogado.getRole());
                mostrarAlertaSucesso("Sucesso", "Avaliação cadastrada com sucesso!");
                mostrarMenuPrincipal();
            } catch (NumberFormatException ex) {
                mostrarAlertaErro("Erro de Entrada", "Valor e peso devem ser numéricos.");
            } catch (Exception ex) {
                mostrarAlertaErro("Erro", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(txtCodigoTurma, txtNome, cbTipo, txtValor, txtPeso, btnSalvar);
        stage.setScene(new Scene(vbox, 400, 350));
    }

    private void mostrarTelaConfigPersistencia() {
        VBox vbox = criarLayoutBase("Configurar Persistência");

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("TXT", "XML", "JSON");
        cbTipo.setPromptText("Selecione o Formato");

        Button btnSalvar = new Button("Aplicar");
        btnSalvar.setOnAction(e -> {
            try {
                // CORRIGIDO: Passando o Role ao invés do Username
                controller.configurarPersistencia(cbTipo.getValue(), usuarioLogado.getRole());
                mostrarAlertaSucesso("Sucesso", "Persistência configurada para " + cbTipo.getValue());
                mostrarMenuPrincipal();
            } catch (Exception ex) {
                mostrarAlertaErro("Erro", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(cbTipo, btnSalvar);
        stage.setScene(new Scene(vbox, 400, 300));
    }

    private void mostrarTelaRelatorios() {
        VBox vbox = criarLayoutBase("Relatórios");

        TextArea txtSaida = new TextArea();
        txtSaida.setEditable(false);
        txtSaida.setPrefHeight(200);

        HBox botoes = new HBox(10);
        Button btnResumo = new Button("Resumo de Avaliações");
        btnResumo.setOnAction(e -> {
            try {
                txtSaida.setText(controller.gerarResumoAvaliacoes(usuarioLogado.getUsername()));
            } catch (Exception ex) {
                mostrarAlertaErro("Erro", ex.getMessage());
            }
        });

        Button btnPesos = new Button("Análise de Pesos");
        btnPesos.setOnAction(e -> {
            try {
                txtSaida.setText(controller.gerarRelatorioPesos(usuarioLogado.getUsername()));
            } catch (Exception ex) {
                mostrarAlertaErro("Erro", ex.getMessage());
            }
        });

        Button btnPersistencia = new Button("Relatório de Persistência");
        btnPersistencia.setOnAction(e -> {
            try {
                txtSaida.setText(controller.gerarRelatorioPersistencia(usuarioLogado.getUsername()));
            } catch (Exception ex) {
                mostrarAlertaErro("Erro", ex.getMessage());
            }
        });

        if (usuarioLogado.getRole().equals("ADMIN")) {
            botoes.getChildren().addAll(btnResumo, btnPesos, btnPersistencia);
        } else {
            botoes.getChildren().addAll(btnResumo, btnPesos);
        }

        vbox.getChildren().addAll(botoes, txtSaida);
        stage.setScene(new Scene(vbox, 600, 400));
    }

    private void mostrarTelaListarTurmas() {
        VBox vbox = criarLayoutBase("Lista de Turmas");
        TextArea txtSaida = new TextArea();
        txtSaida.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Turma t : controller.listarTurmas()) {
            sb.append(t.getCodigo()).append(" - ").append(t.getTitulo()).append("\n");
        }
        if (sb.length() == 0) sb.append("Nenhuma turma cadastrada.");
        txtSaida.setText(sb.toString());

        vbox.getChildren().add(txtSaida);
        stage.setScene(new Scene(vbox, 400, 300));
    }

    private VBox criarLayoutBase(String titulo) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> mostrarMenuPrincipal());

        vbox.getChildren().addAll(btnVoltar, lblTitulo);
        return vbox;
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlertaSucesso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}