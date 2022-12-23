<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="match">
        <h2 class="jh-entity-heading" data-cy="matchDetailsHeading">
          <span v-text="$t('proFootballClubApp.match.detail.title')">Match</span> {{ match.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.match.date')">Date</span>
          </dt>
          <dd>
            <span v-if="match.date">{{ $d(Date.parse(match.date), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.weather')">Weather</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Weather.' + match.weather)">{{ match.weather }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.hostGoals')">Host Goals</span>
          </dt>
          <dd>
            <span>{{ match.hostGoals }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.guestGoals')">Guest Goals</span>
          </dt>
          <dd>
            <span>{{ match.guestGoals }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.events')">Events</span>
          </dt>
          <dd>
            <span>{{ match.events }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.MatchType.' + match.type)">{{ match.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.host')">Host</span>
          </dt>
          <dd>
            <div v-if="match.host">
              <router-link :to="{ name: 'TeamView', params: { teamId: match.host.id } }">{{ match.host.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.guest')">Guest</span>
          </dt>
          <dd>
            <div v-if="match.guest">
              <router-link :to="{ name: 'TeamView', params: { teamId: match.guest.id } }">{{ match.guest.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.hostComposition')">Host Composition</span>
          </dt>
          <dd>
            <div v-if="match.hostComposition">
              <router-link :to="{ name: 'CompositionView', params: { compositionId: match.hostComposition.id } }">{{
                match.hostComposition.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.guestComposition')">Guest Composition</span>
          </dt>
          <dd>
            <div v-if="match.guestComposition">
              <router-link :to="{ name: 'CompositionView', params: { compositionId: match.guestComposition.id } }">{{
                match.guestComposition.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.bestPlayer')">Best Player</span>
          </dt>
          <dd>
            <div v-if="match.bestPlayer">
              <router-link :to="{ name: 'PlayerView', params: { playerId: match.bestPlayer.id } }">{{ match.bestPlayer.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.league')">League</span>
          </dt>
          <dd>
            <div v-if="match.league">
              <router-link :to="{ name: 'LeagueView', params: { leagueId: match.league.id } }">{{ match.league.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.match.cupBoard')">Cup Board</span>
          </dt>
          <dd>
            <div v-if="match.cupBoard">
              <router-link :to="{ name: 'CupBoardView', params: { cupBoardId: match.cupBoard.id } }">{{ match.cupBoard.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="match.id" :to="{ name: 'MatchEdit', params: { matchId: match.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./match-details.component.ts"></script>
