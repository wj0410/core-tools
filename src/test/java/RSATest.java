import io.github.wj0410.core.tools.util.RSAUtils;


public class RSATest {
    public static void main(String[] args) throws Exception {
//        // 生成钥匙对
//        RSAUtils.genkeyPair();
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwZzDo9KI9a1hi7rbI3FyV7l/RmdHGeOH6/tIu+pe79KCaW2iSL1NzL7MGCVSYzOfUw8mpdC9rBlDTB+eWDg1v53F7Wym8nDPZ1aV9/seWo9YZhsYOgv6lNH1ezKy3RQb4rhwFWfHpRI+qptOnxt/vZ06tbumEuPEcWrlyrx9bTCLhbt5bnmA4L3cJmiDXlJcU46Sn4021u6H8WE2EVdCnH1Pxa/h0URJmnOw0dWMD3yKuKgCGeS44T+JqBT64SVpoOLFkvlU+4errWQbqENYsMX8B1EWk0lDDd/Bysnk23rK7+Y13J4zKMImObvoOXVI+25Hk4f0dMshi6jfVyj2lQIDAQAB";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBnMOj0oj1rWGLutsjcXJXuX9GZ0cZ44fr+0i76l7v0oJpbaJIvU3MvswYJVJjM59TDyal0L2sGUNMH55YODW/ncXtbKbycM9nVpX3+x5aj1hmGxg6C/qU0fV7MrLdFBviuHAVZ8elEj6qm06fG3+9nTq1u6YS48RxauXKvH1tMIuFu3lueYDgvdwmaINeUlxTjpKfjTbW7ofxYTYRV0KcfU/Fr+HRREmac7DR1YwPfIq4qAIZ5LjhP4moFPrhJWmg4sWS+VT7h6utZBuoQ1iwxfwHURaTSUMN38HKyeTbesrv5jXcnjMowiY5u+g5dUj7bkeTh/R0yyGLqN9XKPaVAgMBAAECggEAYGruLt5A4IAYjNpgGTV86Nzy2h/Ck8zwun8oSJS99JaT5Wb4o1rKrMTzpK6paceid12bNIZQfq0SC43PcEWF1sf+cm9icGfnkaHYC2eeRSEPt8GUfgvLHWO16UVlN521CWIqRy6oiLceSB4RuECci9QKEcjCOrP89zJx/1WA7OebO0tnQTcy6e/bQAS5E1lnwzvC5oKojQGFeyV2+dmiPoGSHsy5Zt4PbVfNhmEt2A0OnRRduPcpGMw6TeZtkQZdgxvqcjensr39P8o0VOeh7ALLxwVrsRRb5Y1VEZbvujJF0DLkZYvWHjqECvwUXBz8MSxdZQb3iS6WhckB6Q/xHQKBgQDjAogjXfPllrmGqOG3vcS1DymuqjW99q+f1fdgnxK+PhzmcFPO7Q6+Hgj32rdChz2b1hei1wW367Mr1xF9pXU5OISj6r0P2lStTmPrvcRO17Fm/gHSb6um93dNKYO8Rw66eHdhmiTWYZNm6khYio7kZ75zzkieXco1jrA7blL4FwKBgQDaVmQ0+20b9W45fR5uoTti2RgHBrAbd1FgCNDosVXX6ZlERK9Rr0p2Te6wSd+S5t/6YAzXCsvZOvjEjedXfihaUE6mtCI+qcloU+HmrVKAnVgCd6vRbzjjDTaWWfEk8G46qazWh+v4vqzFvwmEdwiG5n93I6k9WWKcju72nVcGMwKBgQCOdR1HzILtFgwxJDQZ4V3bHbvTn38AShlmXZjTKqBRgJsURXNijMNOrXyFRdzxQ/t2mwDUMMmm5KPYpCuWxMlucjhSwVGJ7EJtS59K5hiWPadXcZllHK0Ep5FXe/Duq0zj2gtY1am+WxdsYyOpZLJNRYlwFQjfUQ4/U97mPNKWWwKBgBbN4Sd7fzjlWH7ep5gMdQRtAkpxk2E7BB/Qq+kx5HJprp1nwxv4AAH9P8nf3V8diBVBhPXM5d+pfiQhYiFMRvCv5Jcax1zeJPAlmrmaLrzKMlAHqcV1JW7piWMIv9wI1KqB4FCD38yaNbdtrZPNTvGwD24lJl8G//q0HxdY9abrAoGBAJviCt+kkZ+RY82AKzyZLx6fbaG1zm3iZvSQ8uRPuq/b4/mo31JMk30JFk77uU7xni/rtXm2rxGq07dG2iQu/i5bf2nfKxHnBPkXo9cCBB4SgL2CnwCPueRtVHg+Op30jOCSorFV/ql7asoS5ynNKxXuD8iQbnxDu9MCliyk3wYu";
        String content = "{\"content\":\"测试加密数据JSON\"}";

//        // 使用公钥加密
//        String ctext = RSAUtils.encryptByPbKey(content, publicKey);
//        System.out.println(ctext);
//        // 使用私钥解密
//        System.out.println(RSAUtils.decryptByPvKey(ctext,privateKey));

        // （数字签名） 使用私钥加密，公钥解密
        String pvtext = RSAUtils.encryptByPvKey(content, privateKey);
        System.out.println(pvtext);
        System.out.println(RSAUtils.decryptByPbKey(pvtext, publicKey));
    }
}
