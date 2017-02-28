package br.com.amssolucoes.controlefinanceiro.model;

public class Usuario {
    int codigo;
    String usuario;
    String senha;
    boolean salvarSenha;
    String email;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean getSalvarSenha() {
        return salvarSenha;
    }

    public void setSalvarSenha(boolean salvarSenha) {
        this.salvarSenha = salvarSenha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
