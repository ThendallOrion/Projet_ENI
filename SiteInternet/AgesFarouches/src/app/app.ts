import { Component } from '@angular/core';
import { HeaderComponent } from './components/header/header';
import { HomeComponent } from './pages/home/home';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HeaderComponent, HomeComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  title = 'agesfarouches';
}
