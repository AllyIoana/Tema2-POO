# tema-2-poo-template

## Clasele folosite în plus

* TipuriDeCerere: un enum pentru cererile posibile.
* Fiecare tip de utilizator necesar are propria sa clasă.
* WrongUser: folosită pentru excepția care apare în cadrul creării unei cereri noi.
* DateComparator: folosită pentru sortarea cererilor în funcție de data la care au fost create.
* PriorityComparator: folosit pentru sortarea cererilor din birou, conform celor două criterii, mai întâi după prioritate, apoi după dată.

## Colecțiile folosite
* ArrayList pentru a stoca toate cererile unui utilizator ce se află înașteptare (waiting) sau au fost deja rezolvate (done), deoarece mi s-au părut cel mai ușor de accesat și sortat elementele conform cerinței.
* ArrayList pentru a stoca utilizatorii (în cadrul management-ului), deoarece aceștia nu aveau nevoie de sortare sau altă ordine specială.
* PriorityQueue pentru a stoca cererile din cadrul biroului: acestea trebuie sortate după două criterii și prelucrate într-o ordine fixă. Coada mi s-a părut cea mai bună variantă, deoarece pot adăuga la finalul cozii noile cereri cu prioritate mai mică și extrag pe partea cealaltă ce trebuie finalizat. 
* ArrayList pentru a stoca funcționarii: nu sunt folosiți foarte mult și așa au fost mai ușor de implementat.

## Clasa ManagementPrimarie

Citim fiecare linie în parte din fișierul de intrare și determinăm ce avem de făcut, după cuvintele cheie.

Pentru fiecare caz am creat câte o funcție auxiliară pe care doar o apelăm în main. Acestea au nume sugestive, asemănătoare cu cele din enunțul temei. Pentru a folosi data din cadrul cererilor, am schimbat-o la nevoie din tipul Date în String sau invers.

## Clasa Utilizator

Fiecărui utilizator îi corespund două liste de cereri: cele care se află în așteptare și cele care au fost finalizate.
Există mai multe tipuri de utilizatori, fiecare având clasa sa, ce o extinde pe aceasta.

Pentru a scrie o cerere, trebuie verificată posibilitatea acestei acțiuni: dacă un tip de utilizator poate înainta o cerere de un anumit tip.

## Note
Testarea se face prin rularea programului pe fișierele din src/main/resources/input și compararea fișierelor din src/main/resources/references cu src/main/resources/output. Pentru o bună funcționare, trebuie ca directorul de output să existe/să fie creat în prealabil. Cum GitHub nu admite existența unui director gol, asigurați-vă că în permanență la un *push* veți avea ceva în el (de exemplu, fișierul *ignore* care nu afectează rularea programului dar menține persistența directorului de output).
