## Käivitamiseks vajalik:
- MAVEN
- Java 17

## Projekti käivitus:
1. Projekti ehitus Maveniga:
    - Juurkaustas käsurealt käivitada käsud:
      ```
      mvn compile
      mvn package
      ```
    - Ehitatakse projekti JAR-fail kausta `/target`

2. Rakenduse käivitamine:
    - Kaustas `/target` käivitada käsurealt projekti JAR-fail järgmise käsuga:
      ```
      java -jar adder-0.0.1.jar
      ```
    - Käivitatakse Spring Boot rakendus, mis töötab pordil 8080. Igal käivitusel hävitatakse vana andmetabel ja tehakse uus.
   
3. Testide käivitamine:
    - Juurkaustas käsurealt käivitada käsud:
      ```
      mvn -Dtest=AdderApplicationTests test
      mvn -Dtest=AdderControllerTests test
      
      ```

## Päringud:
- GET päringud endpointidesse:
    - `/api/adder/add/{täisarv1}/{täisarv2}`:
        - Tagastus: päringu liidetavate summa kujul: `{"addable1": täisarv1, "addable1": täisarv2, "sum": täisarv1 + täisarv2}`
        - Kohustuslikud väljad: liidetav1, liidetav2
        - Piirangud: liidetav1 ja liidetav2 täisarvu (INT) tüüpi ning vahemikus 0-100 (kaasa arvatud)

    - `/api/adder/find?ascending={boolean}&findable={otsitavArv}`:
        - Tagastus: Kui on olemas otsitavArv, siis andmebaasis olevad lahendid, kus üks liidetavatest või summa langeb selle arvuga kokku. Ascending määrab ära, kas on järjestatud kasvavas (true puhul) või kahanevas (false puhul) järjekorras summa järgi. Tagastuse kuju: `[{tehe1}, {tehe2}, ..., {tehe n}]`
        - Kohustuslikud väljad: kasvav
        - Piirangud: otsitavArv täisarvu (INT) tüüpi ning vahemikus 0-100 (kaasa arvatud). kasvav boolean tüüpi (true või false)
