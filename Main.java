// java.io paketi, giriş (input) ve çıkış (output) işlemleri için temel sınıfları içerir.
// Bu uygulamada, istemciye veri göndermek için PrintWriter ve OutputStream sınıflarını kullanır.
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

// java.net paketi, ağ (network) programlama için sınıflar sağlar.
// ServerSocket, gelen bağlantıları dinlemek için kullanılırken, Socket istemciyle iletişim kurmak için kullanılır.
import java.net.ServerSocket;
import java.net.Socket;

// java.nio.charset paketi, karakter kodlama ve çözme işlemleri için sınıfları içerir.
// StandardCharsets.UTF_8 ile metin verisinin doğru bir şekilde kodlandığından emin oluruz.
import java.nio.charset.StandardCharsets;


public class Main {

    /**
     * * `PORT` değişkeni, sunucunun dinleyeceği port numarasını belirler.
    */
    private static final int PORT = 1989;


    public static void main(String[] args) {

        // Bu yapı, kaynakların (bu durumda ServerSocket) işi bittiğinde veya bir hata oluştuğunda
        // otomatik olarak kapatılmasını sağlar. Bu, kaynak sızıntılarını önlemek için kritik öneme sahiptir.
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Web sunucusu " + PORT + " portunda başlatıldı. Bağlantılar bekleniyor...");


            // Sunucunun sürekli çalışmasını ve yeni istemci bağlantılarını dinlemesini sağlayan sonsuz bir döngüdür.
            while (true) {
                try {
                    // `accept()` metodu, bir istemci bu porta bağlanana kadar programın akışını durdurur (engeller).
                    // Bağlantı kurulduğunda, bu metod istemci ile sunucu arasında iletişim kurmak için kullanılan
                    // yeni bir `Socket` nesnesi döndürür.
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Yeni bir istemci bağlandı: " + clientSocket.getInetAddress().getHostAddress());

                    // Yeni bağlantıyı işlemek için `handleClient` metodunu çağırır.
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.err.println("İstemci bağlantı hatası: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // `eğer hata olursa hata mesajı verilir
            System.err.println("Sunucu başlatılamadı: " + e.getMessage());
        }
    }

    /**
     * * Bu metod, `main` metodundan çağrılır ve belirli bir istemci (`clientSocket`) ile iletişimi yönetir.
     */
    private static void handleClient(Socket clientSocket) {
        // try-with-resources yapısı ile `OutputStream` ve `PrintWriter` kaynakları otomatik olarak kapatılır.
        try (

                // İstemciye ikili (byte) veri akışı göndermek için bir nesne oluşturur.
                OutputStream outputStream = clientSocket.getOutputStream();

                // `OutputStream`'i sarar ve metin (String) verilerini kolayca yazmayı sağlar.
                // `true`: `auto-flush` modunu açar. Her `println` çağrısından sonra veriler hemen gönderilir.
                // `StandardCharsets.UTF_8`: Karakterlerin Unicode (UTF-8) olarak kodlanmasını sağlar,
                // böylece Türkçe karakterler gibi özel karakterler doğru şekilde görüntülenir.
                PrintWriter out = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
        ) {

            // Bu, HTTP yanıtının ilk satırıdır ve istemciye işlemin başarılı olduğunu (200 OK) bildirir.
            out.println("HTTP/1.1 200 OK");

            // İstemciye gönderilen verinin tipinin HTML olduğunu ve karakter kodlamasının UTF-8 olduğunu bildirir.
            out.println("Content-Type: text/html; charset=UTF-8");

            // Bu boş satır, HTTP başlıkları ile HTML içeriği (gövde) arasındaki zorunlu ayrımı sağlar.
            out.println();

            // Dinamik olarak HTML içeriğini oluşturan metodu çağırır.
            String htmlContent = generateHtmlContent();

            // Oluşturulan HTML içeriğini istemciye gönderir.
            out.println(htmlContent);

        } catch (IOException e) {
            System.err.println("İstemciye yanıt gönderirken hata oluştu: " + e.getMessage());
        }

        finally {
            try {
                // **clientSocket.close();**
                // `finally` bloğu, bir hata oluşsa bile bu kodun çalışmasını garanti eder.
                // İstemci soketini kapatarak kaynakları serbest bırakır ve bağlantıyı sonlandırır.
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("İstemci soketi kapatılamadı: " + e.getMessage());
            }
        }
    }

    /**
     * **private static String generateHtmlContent()**
     * * Bu metod, sunucunun istemciye göndereceği HTML içeriğini oluşturur.
     */
    private static String generateHtmlContent() {
        return """
            <!DOCTYPE html>
            <html lang="tr">
            <head>
                <meta charset="UTF-8">
                <title>Web Sunucusu</title>
                <style>
                    body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; color: #333; margin: 40px; }
                    h1 { color: #0056b3; }
                    h2 { color: #d9534f; }
                    .bio-container {
                        border: 2px solid #ccc;
                        padding: 20px;
                        border-radius: 10px;
                        background-color: #fff;
                        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                    }
                    .bio-text {
                        color: #555;
                        font-family: 'Georgia', serif;
                        line-height: 1.6;
                    }
                </style>
            </head>
            <body>
                <h1>Adı Soyadı - Emre Celik</h1>
                <h2>Öğrenci No - 12461</h2>
                <div class="bio-container">
                    <p class="bio-text">
                        Bu, kendi web sunucumu oluşturma projem kapsamında yazdığım kısa bir biyografidir. 
                        Java ve socket programlama kullanarak, herhangi bir üçüncü taraf kütüphane kullanmadan 
                        nasıl bir web sunucusu çalıştırabileceğimi öğreniyorum. Bu sunucu, temel HTTP isteğini 
                        karşılayarak bu HTML sayfasını tarayıcıya gönderebilmektedir.
                    </p>
                </div>
            </body>
            </html>
            """;
    }
}