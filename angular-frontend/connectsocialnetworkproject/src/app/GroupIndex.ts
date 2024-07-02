export interface GroupIndex {
  name: string;
  description: string;
  createdAt: string;
  suspendedReason?: null;
  databaseId?: number;
  suspended: boolean,
  deleted: boolean,
  pdfDescription: string;
  postCount: number;
  likesCount: number;

}
