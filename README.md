# 🧪 Les Carnets de l'Apothicaire

> Projet d'apprentissage sur l'**authentification** et l'**autorisation** web, autour d'**OAuth2** et de **PKCE**, avec Spring Boot & Spring Security.

L'objectif n'est **pas** de livrer un produit complet, mais de **mettre en pratique et documenter** des notions de sécurité étudiées en théorie, via un exemple volontairement simple : un recueil de concoctions d'apothicaire dont l'accès est protégé selon l'identité et le rôle de l'utilisateur. 🔐

---

## 🎯 Contexte & objectif

Ce projet est né d'une envie de **comprendre concrètement** :

- 🔑 le protocole **OAuth2** et le flux *Authorization Code*
- 🧩 le mécanisme **PKCE** (et quand il s'applique)
- 🪪 la différence entre **être authentifié** (prouver son identité) et **être autorisé** (avoir le droit d'agir)
- 🛡️ les **bons réflexes de sécurité** (secrets externalisés, contrôle côté serveur, CSRF)

Le thème de l'apothicaire n'est qu'un support : le cœur du projet est la **sécurité** et sa **documentation**.

### ℹ️ Note sur PKCE

PKCE a été **étudié** mais n'est **pas actif** ici : l'implémentation utilise **GitHub**, qui repose sur de l'OAuth2 "pur" avec un **client confidentiel** (protégé par un `client-secret`). PKCE vise les **clients publics** (apps mobiles, SPA) incapables de garder un secret. Détails dans [`docs/architecture.md`](docs/architecture.md).

---

## ✨ Fonctionnalités

- 🔓 Connexion via **GitHub** (OAuth2, Authorization Code)
- 🧵 Gestion de **session** + déconnexion sécurisée
- 🌐 Pages **publiques** et pages **protégées**
- 👥 Deux rôles : **Apprenti** (lecture) et **Maître** (zone de gestion)
- ⚙️ Attribution automatique du rôle à la connexion
- 🚫 Page d'erreur **403** personnalisée
- 🎨 Interface **Thymeleaf** affichant les éléments selon le rôle

## 🛠️ Technologies

| Techno | Rôle |
|--------|------|
| ☕ Java 21 | Langage |
| 🍃 Spring Boot 4.1.1 | Framework |
| 🔒 Spring Security (+ OAuth2 Client) | Sécurité |
| 🖼️ Thymeleaf | Templates HTML |
| 📦 Maven | Build & dépendances |

---

## 🏗️ Architecture

Requête HTTP
   │
   ▼
🔒 Spring Security   ← authentification + contrôle d'accès (filtres)
   │
   ▼
🎮 Controller        ← reçoit la requête, prépare la vue
   │
   ▼
🧠 Service           ← logique métier
   │
   ▼
🗄️ Repository        ← accès aux données (en mémoire)

Le flux OAuth2 détaillé (+ PKCE) est dans [`docs/architecture.md`](docs/architecture.md).

---

## 🛡️ Sécurité en bref

| Actif | Protection |
|-------|------------|
| 🔑 `client-secret` | Externalisé en variable d'environnement, jamais dans le code |
| 🔒 Pages protégées | Contrôle serveur `authenticated()` |
| 👑 Zone de gestion | Contrôle de rôle serveur `hasRole("MAITRE")` |
| 🧵 Session | Déconnexion POST protégée par token CSRF |
| 🎫 Jeton d'accès | Échange serveur-à-serveur (Authorization Code) |

---

## 🚀 Installation & configuration

### Prérequis

- ☕ JDK 21
- 🐙 Un compte GitHub

### Étapes

1. 🔧 Créer une **OAuth App** sur GitHub :
   - Homepage URL : `http://localhost:8080`
   - Authorization callback URL : `http://localhost:8080/login/oauth2/code/github`
2. 🔐 Définir deux variables d'environnement :
   - `GITHUB_CLIENT_ID`
   - `GITHUB_CLIENT_SECRET`
3. 👑 (Optionnel) Renseigner le(s) pseudo(s) Maître dans `ApothecaireUserService`
4. ▶️ Lancer :
   ```bash
   ./mvnw spring-boot:run
   ```
5. 🌐 Ouvrir `http://localhost:8080`

## 🧪 Tests de sécurité (manuels)

| # | Scénario | Attendu | Résultat |
|---|----------|---------|----------|
| 1 | `/concoctions` sans session | Redirection GitHub | ✅ |
| 2 | `/concoctions` connecté | Accès accordé | ✅ |
| 3 | `/gestion` en Apprenti | 403 Forbidden | ✅ |
| 4 | Déconnexion puis accès protégé | Ré-authentification | ✅ |

## 🧭 Limites & pistes d'amélioration

- 💾 **Pas de persistance** : données et rôles en mémoire → évolution vers une BDD (H2/PostgreSQL)
- 👤 **Rôles en dur** (liste de pseudos) → évolution vers une gestion en base
- 🧩 **PKCE non actif** (client confidentiel + GitHub) → testable avec un provider OIDC
- 🤖 **Tests manuels uniquement** → à automatiser (JUnit + Spring Security Test)
- 🔒 **HTTP en local** → prod nécessiterait HTTPS + cookie `Secure`
- ➕ **Création/suppression de concoctions** non encore implémentée

## 📚 Ce que ce projet m'a appris

- Mettre en place une **authentification déléguée** sans gérer de mots de passe
- Comprendre le **flux OAuth2** et le rôle de chaque acteur
- Distinguer **authentification** et **autorisation** en pratique
- Appliquer une **hygiène des secrets** (externalisation, rotation)
- **Documenter** une démarche de sécurité de façon crédible
