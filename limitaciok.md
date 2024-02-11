# Technikai limitációk
Bár szerettem volna elkerülni, sajnos több technikai limitációnak nézünk elébe. 😐  
Mivel ez egy kis projekt, amin csak szabadidőben dolgozom, így igyekszem elsősorban a funkcionalitás felé terelni a progress-t és a finomításokra csak később veszem a fáradtságot.  

# Visszajelentés 📢
Nos, igen. Az egyik legnagyobb hátránya projektnek, hogy nem igazán tud visszajelenteni azzal kapcsolatban, hogy ha elakadt.  
Jelenleg az egyetlen dolog, amit tudunk, hogy egy adott Condition érvényes volt-e, illetve, hogy a feladatnak sikerült-e lefutnia.  
Ha esetleg valahol több Condition is szerepel, abban az esetben nem tud visszajelentést adni arról, hogy mely Condition nem tudott teljesülni.  

**Példa:**
```json
{
    "conditions": [
        {
            "type": "in_region",
            "data": {
                "world": "world",
                "value": "pvp_region"
            }
        },
        {
            "type": "has_permission",
            "value": "blessings.regeneration"
        }
    ]
}
```
Az alábbi esetben a kód 2 Condition-t is tartalmaz (_in_region, has_permission_). Ha esetleg az első Condition teljesül és a játékos a _pvp_region_ nevű területen tartózkodik,
azonban a második, jogosultságot ellenőrző Condition nem tud teljesülni, mert nem rendelkezik a _blessings.regeneration_ jogosultsággal, akkor abból a könyvtár csak annyit érzékel, hogy valamelyik Condition _FAILED_ értéket adott,
illetve, hogy az ``execute(executor, action)`` _false_ értékkel tért vissza.  
Ennek a megoldásán dolgozom, viszont időigényes feladat és első az, hogy működjön a könyvtár.  


# Operátorok hiánya ⁉
A másik nagy limitáció, ami remélhetőleg hamarosan megoldásra kerül, az az operátorok hiánya.  
Szeretném, ha bizonyos feltételeknél több értéket is össze tudnánk fűzni, mint lehetséges opció, vagy, mint szükséges feltétel, viszont erre egy operátor rendszert kell kidolgoznom.  
Elsőnek egy **VAGY**, illetve egy **ÉS** operátort szeretnék elkészíteni, amik lehetővé teszik, hogy pl. egy levédésnél ne csak egy lehetséges zóna legyen, ahol az adott Action lefuttatható.  


