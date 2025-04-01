import {Component, OnInit } from '@angular/core';
import { ReviewService } from '../service/review.service';
import { TableModule } from 'primeng/table';
import { NgChartsModule } from 'ng2-charts';
import {ChartData, ChartDataset} from 'chart.js';
import { NgIf } from '@angular/common';


@Component({
  selector: 'app-dashboard',
  imports: [TableModule, NgChartsModule, NgIf],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  // Configuração de gráficos
  sentimentChart: ChartData<'bar', number[], string> = { labels: [], datasets: [] };
  genreChart: ChartData<'pie', number[], string> = { labels: [], datasets: [] };
  comparisonChart: ChartData<'bar', number[], string> = { labels: [], datasets: [] };

  loadingSentiment = true;
  loadingGenre = true;
  loadingComparison = true;

  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.loadSentimentChart();
    this.loadGenreChart();
    this.loadScoreComparison();

  }

  loadSentimentChart() {
    this.reviewService.getSentimentDistribution().subscribe(data => {
      this.sentimentChart = {
        labels: Object.keys(data),
        datasets: [
          { data: Object.values(data), label: 'Sentimentos' }
        ]
      };
      this.loadingSentiment = false;
    });
  }

  loadGenreChart() {
    this.reviewService.getGenreDistribution().subscribe(data => {
      this.genreChart = {
        labels: Object.keys(data),
        datasets: [
          { data: Object.values(data), label: 'Gêneros' }
        ]
      };
      this.loadingGenre = false;
    });
  }

  loadScoreComparison() {
    this.reviewService.getScoreComparison().subscribe(data => {
      this.comparisonChart = {
        labels: ['Audience Score', 'TomatoMeter'],
        datasets: [
          {
            data: [data.audienceAverage, data.tomatoAverage],
            label: 'Média das Avaliações'
          }
        ]
      };
      this.loadingComparison = false;
    });
  }




}
