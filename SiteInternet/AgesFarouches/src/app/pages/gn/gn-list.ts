import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Gn } from './gn.model';
import { GnService } from './gn.service';

type GnFilter = 'tous' | 'a-venir' | 'termines';

@Component({
  selector: 'app-gn-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gn-list.html',
  styleUrl: './gn-list.scss'
})
export class GnListComponent implements OnInit {
  gnList: Gn[] = [];
  loading = true;
  error = false;

  filter: GnFilter = 'tous';

  constructor(private gnService: GnService) {}

  ngOnInit(): void {
    this.gnService.getGnList().subscribe({
      next: (data) => {
        this.gnList = data;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  setFilter(filter: GnFilter): void {
    this.filter = filter;
  }

  get filteredGnList(): Gn[] {
    const now = new Date();

    if (this.filter === 'a-venir') {
      return this.gnList.filter((gn) => new Date(gn.Date_Fin) >= now);
    }

    if (this.filter === 'termines') {
      return this.gnList.filter((gn) => new Date(gn.Date_Fin) < now);
    }

    return this.gnList;
  }
}