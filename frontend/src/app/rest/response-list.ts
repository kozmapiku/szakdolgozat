export interface ResponseList<T> {
	timestamp: string;
	status: string;
	length: number;
	data: T[];
}
