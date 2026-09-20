import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Gn } from '../models/gn.model';

@Injectable({
  providedIn: 'root'
})
export class GnService {
  constructor(private http: HttpClient) {}

  // Ajuste '/gn' si le vrai endpoint de l'API a un autre nom (ex: '/gns', '/events').
  getGnList(): Observable<Gn[]> {
    return this.http.get<Gn[]>(`${environment.apiUrl}/gn`);
  }
}
