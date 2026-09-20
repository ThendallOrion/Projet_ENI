import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Gn } from './gn.model';

@Injectable({
  providedIn: 'root'
})
export class GnService {
  constructor(private http: HttpClient) {}

  getGnList(): Observable<Gn[]> {
    // Cache-Control: no-cache force le navigateur à revalider/retélécharger
    // au lieu de servir un 304, le temps d'éliminer le cache comme cause possible.
    const headers = new HttpHeaders({ 'Cache-Control': 'no-cache' });
    return this.http.get<Gn[]>(`${environment.apiUrl}/gn`, { headers });
  }
}