[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/VYS_GaEs)
# Université Côte d'Azur - DS4H - 
# MASTER MIAGE NUMRES
## Cours de Programmation Mobile Moderne - Leo Donati

### TD3 : Application Kotlin Multiplateforme


**Objectif du TD3** : faire de l'application PizzApp (td2) une application multiplateforme qui puisse tourner sur
- Android
- Navigateur Web
- Desktop (MacOS, Windows, Linux)

#### Consignes
- A faire en binôme
- Utiliser le repository du github classroom en suivant [ce lien]() pour le binôme
- Décrire dans ce ReadMe les détails de votre projet et comment vous avez surmonté les difficultés (typiquement concernant certains packages comme Room)
- Ajouter le lien vers une vidéo (Youtube ou autre) avec une démo de votre projet où l'on voit son déploiement sur differentes plateformes

#### Description du projet
Ce projet est une solution multi-plateforme se déclinant en deux parties principales : Android et Desktop. Initialement, une version Web était envisagée, mais en raison de contraintes de temps et de problèmes de compatibilité (notamment avec la dépendance WasmJs), elle n'a pas été implémentée. L'objectif principal est de démontrer la portabilité de la solution en implémentant la logique de stockage et de persistance sur deux plateformes distinctes.

La gestion du stockage est centralisée dans un module commun qui contient toute la logique liée à la persistance des données. La base de données y est définie dans le fichier Build Gradle, où sont précisés le package et le nom, et est configurée à l'aide de SQL Delight. Dans ce module, trois tables sont créées à partir de commandes SQL (CREATE TABLE), accompagnées des fonctions nécessaires pour leur utilisation ultérieure.

Lors du build, ces instructions SQL sont interprétées pour générer des classes Kotlin représentant des requêtes. Ces classes sont ensuite regroupées dans des repositories spécifiques au module commun et sont utilisées dans les modules Android et Desktop via une interface commune. Chaque plateforme utilise sa propre instance de driver SQL Delight, créée par une factory, pour accéder à la base de données et interagir avec ces repositories.

Enfin, le module commun contient également les modèles de données (DTOs) qui facilitent le transfert des données, de manière similaire à ce qui se fait avec Spring. En revanche, la logique de présentation et les view models ne sont pas partagés entre les plateformes, à l'exception de certaines parties concernant les images, puisque chaque plateforme gère ces éléments différemment. Notons également que certaines difficultés ont été rencontrées, comme la nécessité de lancer la compilation en mode administrateur sous PowerShell en raison d'un verrou sur un fichier utilisé par SQL Delight.

#### Vidéo
PizzaApp Démo - SUKHORUKOVA Irina & CHELLABI Ayoub : https://youtu.be/rrrtZtG_Zwc
