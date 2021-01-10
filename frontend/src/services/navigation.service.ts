import Navigo from 'navigo';

export class NavigationService {
  private static instance: NavigationService | null = null;
  router: Navigo;

  static getInstance(): NavigationService {
    if (this.instance == null) {
      this.instance = new NavigationService();
    }

    return this.instance;
  }

  constructor() {
    this.router = new Navigo('/');
  }
}
