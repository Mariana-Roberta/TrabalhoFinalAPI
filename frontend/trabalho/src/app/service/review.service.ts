import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Review} from '../model/review.model';
import {Page} from '../model/page.model';


@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:8080/api/reviews';

  constructor(private http: HttpClient) {}

  getFilteredReviews(
    title?: string,
    criticName?: string,
    genre?: string,
    isTopCritic?: boolean
  ): Observable<Review[]> {
    let params = new HttpParams();

    if (title) params = params.set('title', title);
    if (criticName) params = params.set('criticName', criticName);
    if (genre) params = params.set('genre', genre);
    if (isTopCritic !== undefined) params = params.set('isTopCritic', isTopCritic);

    return this.http.get<Review[]>(`${this.apiUrl}/filter`, { params });
  }

  getReviews(page: number = 0, size: number = 10): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}?page=${page}&size=${size}`);
  }


  getSentimentDistribution(page: number = 0, size: number = 10): Observable<{ negative: number; positive: number }> {
    return this.http.get<{ negative: number; positive: number }>(`${this.apiUrl}/sentiment?page=${page}&size=${size}`);
  }


  getGenreDistribution(page: number = 0, size: number = 10): Observable<{ [key: string]: number }> {
    return this.http.get<{ [key: string]: number }>(
      `${this.apiUrl}/genre-distribution?page=${page}&size=${size}`
    );
  }

  getScoreComparison(page: number = 0, size: number = 10): Observable<{ tomatoAverage: number; audienceAverage: number }> {
    return this.http.get<{ tomatoAverage: number; audienceAverage: number }>(
      `${this.apiUrl}/score-comparison?page=${page}&size=${size}`
    );
  }


  getReviewStatistics(): Observable<{ [key: string]: any }> {
    return this.http.get<{ [key: string]: any }>(`${this.apiUrl}/statistics`);
  }
}
