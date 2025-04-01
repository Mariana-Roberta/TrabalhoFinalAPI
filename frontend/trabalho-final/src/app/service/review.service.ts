import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../model/review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:8080/api/reviews';

  constructor(private http: HttpClient) {}

  getSentimentDistribution() {
    return this.http.get<{ [key: string]: number }>(`${this.apiUrl}/sentiment`);
  }

  getGenreDistribution() {
    return this.http.get<{ [key: string]: number }>(`${this.apiUrl}/genre-distribution`);
  }

  getScoreComparison(): Observable<{ audienceAverage: number, tomatoAverage: number }> {
    return this.http.get<{ audienceAverage: number, tomatoAverage: number }>(
      'http://localhost:8080/api/reviews/score-comparison'
    );
  }



}
