# Android Monitoring
## 1. Wizja

### 1.1 Założenia
Produktem będzie system do monitorowania telefonu komórkowego z systemem Android. System będzie składał się z części klienckiej działającej na telefonie, oraz części serwerowej działającej na PCie.

### 1.2 Monitorowane parametry
- użycie procesora (przebieg z czasem - diagram 7 per proces)
- zużycie pamięci (komponent z kołami)
- żyroskop (przebieg z czasem)
- temperatura otoczenia (komponent z termometrem)
- poziom baterii (komponent z termometrem)
- siła sygnału wifi (diagram kołowy)
- lokalizacja gps (komponent z konsolą)
- siła pola magnetycznego
- nowe zdarzenia (komponent z konsolą)

### 1.3 Klient
Interfejs graficzny aplikacji będzie ograniczał się do jednego widoku, na którym będzie można wpisać parametry połączenia (ip) oraz zaznaczyć parametry z listy, które mają być monitorowane. Po zatwierdzeniu wybrane parametry będą odczytywane i wysyłane do serwera za pomocą komponentu komunikacji napisanego w poprzedniej iteracji projektu.
Każdy parametr będzie odczytywany co pewien określony czas, przykładowo co 1s.
Całość kodu będzie napisana języku Java z użyciem wbudowanych funkcji Androidowych do pobierania poszczególnych parametrów.

### 1.4 Serwer
Aplikacja serwera będzie desktopową aplikacją wyświetlającą pomiary parametrów telefonu. 
Aplikacja będzie odbierała wyniki pomiarów parametrów z telefonu. 
Wykres dla każdego parametru będzie tworzony w momencie odebrania pierwszego pomiaru parametru. 
Interfejs graficzny bedzie napisany w bibliotece Swing języka Java. Wykresy będą się włączały na osobnych panelach aby każdy mógł być zamknięty jeśli klient się rozmyśli i nie będzie chciał obserwować jakiejś danej.
