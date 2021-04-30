# RES-2021-Lab3-SMTP

> Auteurs : Robin Gaudin & Noémie Plancherel

## Description du projet

Dans le cadre d'un laboratoire durant le module de Réseaux (RES) à l'HEIG-VD, nous avons dû développer un client SMTP en Java par groupe de 2. Cette application utilise la Socket API pour communiquer avec un serveur SMTP MockMock.

## Mise en place du serveur MockMock



## Utilisation du programme

### Liste des fichiers pour la configuration

Les fichiers contenant la configuration pour le client SMTP se trouvent dans le répertoire config à la base du projet :

![](figures/dossierConfig.png)

### Configurer les messages à envoyer

Les messages sont préparés dans le fichier `messages.utf8` et sont constitués d'une première ligne qui contiendra la sujet du mail. A noter qu'il n'est pas obligatoire de mettre `Subject:` devant, le sujet sera la ligne entière. Les lignes suivantes constitueront le contenu du mail. Pour terminer et séparer les messages, il suffit de mettre une ligne égale à `==`. 

Le fichier se présente donc sous la forme suivante:

![](figures/messages.png)

### Configurer la liste des destinataires

Le fichier `victims.utf8` contient toutes les adresses mails des gens faisant partie du prank. Elles sont séparées par un simple retour à la ligne. Voici un exemple :

![](figures/victims.png)

### Configurer les options du client SMTP

Le dernier fichier de configuration concerne les configurations du client SMTP. Pour changer l'adresse IP du serveur, il faut remplacer la valeur du champ `smtpServerAddress`. Il est par défaut à `localhost` pour un client en local. Le port utilisé par le serveur SMTP est à configurer dans le champ `smtpServerPort`. La valeur `numberOfGroups` est le nombre de mails à générer. Il faudra que le nombre d'adresses mails du fichier `victims.utf8` / le nombre de groupe soit plus grand ou égal à 3. Enfin, le champ `witnessesToCC` .....

![](figures/configProperties.png)

### Lancer l'application



## Implémentation

### Structure du projet

Tous les fichiers .java se trouvent dans le répertoire src, qui se situe à la base du projet :

![](figures/dossierSrc.png)

Les fichiers sources ont été séparés en différents package pour une meilleure organisation des fichiers.

### Package config



### Package model.mail



### Package model.prank



### Package smtp



### Fichier MailRobot

