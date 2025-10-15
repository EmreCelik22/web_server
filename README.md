# Java Web Sunucu Uygulaması

Bu proje, temel bir web sunucu uygulaması oluşturmak için Java'nın **`ServerSocket`** ve **`Socket`** sınıflarını kullanmaktadır. Bu uygulama, istemciden gelen HTTP isteğine cevap olarak dinamik bir HTML sayfası gönderir. Bu sayfa, basit bir biyografi içeren HTML içeriği sunar.

## Proje Hakkında

Bu basit Java uygulaması, ağ programlaması ve HTTP yanıtı oluşturma konularında temel bir anlayış sağlamayı amaçlamaktadır. Uygulama, hiçbir üçüncü taraf kütüphane kullanmadan, tamamen Java'nın yerleşik sınıflarıyla bir HTTP sunucusu kurmanıza olanak tanır. Sunucu, gelen bağlantıları dinler ve her yeni bağlantı için istemciye bir HTML sayfası gönderir.

### Özellikler

- HTTP/1.1 sunucu protokolü kullanır.
- Dinamik HTML içeriği oluşturur.
- UTF-8 karakter seti desteği ile Türkçe karakterleri doğru şekilde işler.
- Web sayfasında kısa bir biyografi içeriği bulunmaktadır.

## Kullanım

### Gereksinimler

- Java 8 veya daha yüksek sürümü.
- Bir terminal veya IDE (Eclipse, IntelliJ vb.) kullanarak programı çalıştırabilirsiniz.


## Ana Sınıf - Main.java

- ServerSocket : Sunucu bağlantılarını dinler ve istemciden gelen bağlantıyı kabul eder.

- Socket : İstemci ile iletişimi sağlar.

- PrintWriter : İstemciye HTML yanıtlarını gönderir.

- OutputStream : Verilerin ağ üzerinden iletilmesini sağlar.

Sunucunun başlangıcında 1989 portu üzerinden gelen istemci bağlantılarını dinler. Her bağlantı kabul edildikçe, handleClient metodu çağrılır ve istemciye HTTP yanıtı gönderilir. Bu yanıt, HTML içeriği ve başlıkları içerir.
