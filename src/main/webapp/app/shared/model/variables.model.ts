export interface IVariables {
  id?: string;
  leagueFirstPlacePrize?: number | null;
  leagueSecondPlacePrize?: number | null;
}

export class Variables implements IVariables {
  constructor(public id?: string, public leagueFirstPlacePrize?: number | null, public leagueSecondPlacePrize?: number | null) {}
}
