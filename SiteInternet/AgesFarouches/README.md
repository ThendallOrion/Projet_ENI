# Intégration dans ton projet Angular

Ces fichiers sont prévus pour un projet créé avec `ng new` (Angular 17+, standalone components).

## Étapes

1. Crée ton projet si ce n'est pas déjà fait :
   ```
   ng new agesfarouches --routing --style=scss --strict
   ```

2. Copie les dossiers/fichiers de cette archive dans ton `src/` en écrasant :
   - `src/app/app.component.ts`
   - `src/app/app.component.html`
   - `src/app/app.component.scss`
   - `src/app/components/header/` (nouveau dossier)
   - `src/app/pages/home/` (nouveau dossier)
   - `src/styles.scss`

3. Ajoute ton logo dans `src/assets/images/logo.png` (crée le dossier `assets/images` s'il n'existe pas). Le header pointe vers `assets/images/logo.png`.

4. Lance le projet :
   ```
   ng serve
   ```
   Puis ouvre `http://localhost:4200`.

## Ce qui est fait
- Bandeau 1 (`HeaderComponent`) : logo rond + nom de l'association + menu de navigation.
- Bandeau 2 + contenu (`HomeComponent`) : titre "Accueil" sur fond turquoise clair, puis texte + liens vers les actus.

## Ce qui reste à faire (pas demandé pour l'instant)
- Rendre le menu déroulant "Grandeurs Nature et Murder Parties" fonctionnel (sous-menu).
- Ajouter le routing vers les autres pages (`L'association`, etc.).
- Brancher les liens d'actualités sur de vraies pages/articles.
