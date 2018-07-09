package me.jmll.utm.form;

/**
 * POJO con dos atributos getters/setters:
 * usuario: String, privado
 * password String, privado
 * */
public class LoginForm
{
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}