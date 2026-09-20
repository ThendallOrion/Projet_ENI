import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Gn } from '../../core/models/gn.model';
import { GnService } from '../../core/services/gn.service';

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
}
