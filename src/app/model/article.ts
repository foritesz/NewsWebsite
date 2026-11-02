export interface Article {
  id: number;
  views: number;
  author: number | null;
  category: string;
  frontpage: boolean;
  title: string;
  introduction: string;
  content: string;
  imageUrl: string;
  publishDate: Date | string;
}
