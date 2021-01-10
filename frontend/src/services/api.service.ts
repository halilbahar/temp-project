import { Room } from '../models/room.model';

export class ApiService {
  static get rooms(): Room[] {
    return [
      {
        id: '24a97ad2-d6b1-4703-a21f-34c685abc16a',
        name: 'Room 1',
        players: [
          {
            id: 'lSzsV6e1q_KgZKNV3-tH0RYbUCFfgX79d1jBQ0yj',
            name: 'Lolo'
          }
        ]
      }
    ];
  }
}
