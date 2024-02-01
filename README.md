# actionator
Az alábbi könyvtár lehetővé teszi bizonyos feladatok script-rendszbe történő lebutítását.  
Sokszor akadnak olyan helyzetek, ahol egyszerűen értelmetlennek tűnik hard-codeolni bizonyos feladatokat. Erre a problémára kíván megoldást nyújtani ez a kis könyvtár.  
Rendkívül jól alkalmas olyan feladatok (pl. eventek, minijátékok) funkcióinak egyszerű, gyors megvalósításához.  
Alapveőten multiplatform módon lett felépítve, így akár __Sponge__, valamit __Forge/Fabric__ implementáció is lehetséges.  

A működése a __Minecraft__ Datapack rendszerében megszokott formátumhoz és világhoz lett igazítva, így, nagyon könnyű hozzászokni, ha dolgoztál már velük.

# Példa
Az alábbi példában egy áldás plugin, kézzel megírt áldását fogom bemutatni.  
Az áldás az alábbi módon néz ki: először, az áldás induláskor, majd minden másodpercben (_ticking: true_) 3 másodperces __Regeneráció__ potion effektet ad minden olyan játékosra, akik rendelkeznek a _blessings.regeneration_ jogosultsággal.  

A számunkra releváns rész a _tasks_ tömb után kezdődik.  
A __sequence__ beállítás a könyvtár szempontjából irreleváns, mivel az az egyik plugin részét képezi.  
Az __actions__ tömb tartalmaz minden végrehajtandó feladatot. Minden megadott JsonObject egy új __AbstractAction__ (tov. "_action_") osztálynak felel meg.  
A benne található paraméterek alapján dől el, hogy az adott action pontosan melyik feladatnak is felel meg.  

Minden action mellé tartoznak __Condition__-ök. Annak érdekében, hogy a létrehozott action lefusson, minden __Condition__-nek teljesülnie kell.  
Ezek mind az interakciót végrehajtó kontextusából kerülnek ellenőrzésre. (Ha játékos, akkor egy _HAS_PERMISSION_ az adott játékosok fogja ellenőrizni a jog meglétét.)  

```json
{
    "id": "regeneration",
    "display": {
        "name": "§dRegeneráció",
        "lore": [
            "§7Aktiválj egy §dRegeneráció §7áldást!"
        ],
        "icon": {
            "material": "potion",
            "enchanted": true,
            "enchantments": [
                {
                    "enchantment": "unbreaking",
                    "level": 3
                }
            ]
        }
    },
    "properties": {
        "ticking": true,
        "duration": 3600,
        "max_duration": 10800,
        "permission": "blessings.regeneration"
    },
    "tasks": [
        {
            "sqeuence": "start",
            "actions": [
                {
                    "type": "potion_effect",
                    "data": {
                        "value": "regeneration",
                        "duration": 3,
                        "modifier": 2
                    },
                    "conditions": [
                        {
                            "type": "has_permission",
                            "value": "blessings.regeneration.receive"
                        }
                    ]
                }
            ]
        },
        {
            "sequence": "tick",
            "actions": [
                {
                    "type": "potion_effect",
                    "data": {
                        "value": "regeneration",
                        "duration": 3,
                        "modifier": 2
                    },
                    "conditions": [
                        {
                            "type": "has_permission",
                            "value": "blessings.regeneration.receive"
                        }
                    ]
                }
            ]
        }
    ]
}
```

# Action típusok
A végrehajtható interakciók listája még kissé hiányos, de folyamatosan bővül.  
Interkacióktól függően, a hozzájuk tartozó, általuk várt értékek típusai lehetnek közönségesek (String, Integer, Double, stb.), illetve speciálisak is, melyek külön részletezésre kerülnek.  

| Action | Várt érték | Típus | Megjegyezés |
| --- | --- | --- | --- |
| SEND_MESSAGE | value | String | Üzenet küldése az interakciót végrehajtó számára. |
| RUN_COMMAND | value | String | Parancs lefuttatása az interakciót végrehajtón. |
| ECONOMY | data | EconomyActionData | Egyenlegkezelés az interakciót végrehajtón. |
| POTION_EFFECT | data | PotionEffectActionData | Potion effekt hozzáadása az interakciót végrehajtóhoz. |

# Egyedi típusok
Az alábbi szekcióban részletesebben foglalkozunk az egyedi adattípusokkal.  
Abban az esetben, ha egy megadott action végrehajtásához több adatra van szükség, mint 1 (pl. egy potion effekt esetében, ahol szükség van arra, hogy mekkora nagyságút, mennyi ideig szeretnénk hozzáadni) egyedi típusokat alkalmazunk.  
Ezek szolgálnak arra, hogy minden szükséges adat továbbításra kerüljön a plugin részére.  

__EconomyActionData__  
| Várt érték | Típus | Megjegyzés |
| --- | --- | --- |
| type | EconomyActionType | Az interakció típusa (ADD, REVOKE, SET) |
| amount | Double | A kezelni kívánt összeg mennyisége. |

__PotionEffectActionData__ (* opcionális)  
| Várt érték | Típus | Megjegyzés |
| --- | --- | --- |
| value | String | A potion effekt azonosítója |
| duration | Integer | A potion effekt ideje. |
| amplifier | Integer | A potion effekt ereje. |
| ambient* | Boolean | Potion effekt particle mennyiségének növelése. |
| particles* | Boolean | Potion effekt particle engedélyezése. |

# Condition
Az alábbi részben szót ejtünk a __Condition__-ökről.  
Ők segítenek nekünk abban, hogy bizonyos action-ök végrehajtását meghatározott feltételekhez (pl. egyenleg mennyisége, jogosultság) szabhassuk.
Ahhoz, hogy egy action végrehajtódhasson, nem kötelező megadni egyetlen feltételt sem, ám vannak bizonyos helyzetek, mikor ajánlott.  

Jelenleg mindösszesen kettő van implementálva, de ez folyamatosan változik.  

| Condition | Várt érték | Típus | Megjegyzés |
| --- | --- | --- | --- |
| HAS_MONEY | amount | Double | Egyenleg ellenőrzése az interakciót végrehajtón. |
| HAS_PERMISSION | value | String | Jogosultság ellenőrzése az interakciót végrehajtón. |
