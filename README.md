# Android Monitoring
## 1. Wizja

### 1.1 Założenia
Produktem będzie system do monitorowania telefonu komórkowego z systemem Android. System będzie składał się z części klienckiej działającej na telefonie, oraz cześci serwerowej działającej na PCie.

### 1.2 Monitorowane parametry
- użycie procesora
- zużycie pamięci
- żyroskop
- temperatura otoczenia
- poziom baterii
- siła sygnału wifi
- lokalizacja gps
- siła pola magnetycznego

### 1.3 Klient
Interfejs graficzny aplikacji będzie ograniczał się do jednego widoku, na którym będzie można wpisać parametry połączenia (ip) oraz zaznaczyć parametry z listy, które mają być monitorowane. Po zatwierdzeniu wybrane parametry będą odczytywane i wysyłane do serwera.

### 1.4 Serwer
Aplikacja serwera będzie desktopową aplikacją wyświetlającą pomiary parametrów telefonu. Aplikacja będzie odbierała wyniki pomiarów parametrów z telefonu. Wykres dla każdego parametru będzie tworzony w momencie odebrania pierwszego pomiaru parametru.
