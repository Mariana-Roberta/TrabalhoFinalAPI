export interface Review {
  _id: string;

  id: string;
  title: string;
  audienceScore: number | null;
  tomatoMeter: number | null;
  rating: string | null;
  ratingContents: string | null;
  releaseDateTheaters: string; // ISO date string (ex: "2021-12-10T00:00:00")
  releaseDateStreaming: string | null;
  runtimeMinutes: number | null;
  genre: string;
  originalLanguage: string;
  director: string;
  writer: string;
  boxOffice: string | null;
  distributor: string;
  soundMix: string;
  reviewId: number;
  creationDate: string; // ISO date (ex: "2021-06-28")

  criticName: string;
  isTopCritic: boolean;
  originalScore: string;
  reviewState: string;
  publicatioName: string;
  reviewText: string;
  scoreSentiment: string;
  reviewUrl: string;
}
