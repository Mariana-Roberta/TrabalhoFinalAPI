import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../service/review.service';
import { BaseChartDirective } from 'ng2-charts';
import { ChartData } from 'chart.js';
import {NgForOf, NgIf} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Chart, registerables } from 'chart.js';
import {Review} from '../model/review.model';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [BaseChartDirective, NgIf, FormsModule, NgForOf],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  reviews: Review[] = [];

  genreChart: ChartData<'pie', number[], string> = { labels: [], datasets: [] };
  loadingGenre = true;

  scoreChart: ChartData<'doughnut', number[], string> = { labels: [], datasets: [] };
  loadingScore = true;

  sentimentChart: ChartData<'bar', number[], string> = { labels: [], datasets: [] };
  loadingSentiment = true;


  currentPage = 0; // Página inicial
  pageSize = 10; // Tamanho da página

  constructor(private reviewService: ReviewService) {
    Chart.register(...registerables);
  }

  ngOnInit(): void {
    this.loadGenreChart(0, this.pageSize);
    this.loadReviews();
    this.loadScoreChart();
    this.loadSentimentChart();
  }

  loadGenreChart(number: number, pageSize: number) {
    this.loadingGenre = true;
    this.reviewService.getGenreDistribution(this.currentPage, this.pageSize).subscribe(data => {
      this.genreChart = {
        labels: Object.keys(data),
        datasets: [
          { data: Object.values(data), label: 'Gêneros' }
        ]
      };
      this.loadingGenre = false;
    });
  }

  loadReviews(): void {
    this.reviewService.getReviews(this.currentPage, this.pageSize).subscribe((response: Review[]) => {
      this.reviews = response; // ✅ Agora recebe diretamente a lista de objetos
    });
  }

  loadScoreChart() {
    this.loadingScore = true;
    this.reviewService.getScoreComparison(this.currentPage, this.pageSize).subscribe(data => {

      this.scoreChart = {
        labels: ['Tomato Meter', 'Audience Score'],
        datasets: [
          {
            data: [data.tomatoAverage, data.audienceAverage],
            backgroundColor: ['#3a0ca3', '#36A2EB']
          }
        ]
      };

      this.loadingScore = false; // ✅ Agora o gráfico pode ser exibido corretamente

    }, error => {
      console.error('Erro ao buscar os dados:', error);
      this.loadingScore = false; // Evita que o carregamento fique preso caso ocorra erro
    });
  }

  loadSentimentChart(): void {
    this.loadingSentiment = true;
    this.reviewService.getSentimentDistribution(this.currentPage, this.pageSize).subscribe(data => {
      this.sentimentChart = {
        labels: ['Positive', 'Negative'], // Garante a ordem correta
        datasets: [
          {
            data: [data.positive, data.negative], // Agora acessa explicitamente os valores
            backgroundColor: ['#3a0ca3', '#FF6384'], // Azul para positivo, vermelho para negativo
            label: 'Distribuição de Sentimentos'
          }
        ]
      };
      this.loadingSentiment = false;
    }, error => {
      console.error('Erro ao carregar gráfico de sentimentos:', error);
      this.loadingSentiment = false;
    });
  }

  nextPage() {
    this.currentPage++;
    this.loadGenreChart(0, this.pageSize);
    this.loadReviews();
    this.loadScoreChart();
    this.loadSentimentChart();
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadGenreChart(0, this.pageSize);
      this.loadReviews();
      this.loadScoreChart();
      this.loadSentimentChart();
    }
  }

}
