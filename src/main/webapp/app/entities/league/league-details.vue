<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="league">
        <h2 class="jh-entity-heading" data-cy="leagueDetailsHeading">
          <span v-text="$t('proFootballClubApp.league.detail.title')">League</span> {{ league.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.league.clazz')">Clazz</span>
          </dt>
          <dd>
            <span>{{ league.clazz }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.league.events')">Events</span>
          </dt>
          <dd>
            <span>{{ league.events }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.league.finished')">Finished</span>
          </dt>
          <dd>
            <span>{{ league.finished }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.league.start')">Start</span>
          </dt>
          <dd>
            <span v-if="league.start">{{ $d(Date.parse(league.start), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.league.goalScorer')">Goal Scorer</span>
          </dt>
          <dd>
            <div v-if="league.goalScorer">
              <router-link :to="{ name: 'PlayerView', params: { playerId: league.goalScorer.id } }">{{ league.goalScorer.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.league.newsPaper')">News Paper</span>
          </dt>
          <dd>
            <div v-if="league.newsPaper">
              <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: league.newsPaper.id } }">{{
                league.newsPaper.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="league.id" :to="{ name: 'LeagueEdit', params: { leagueId: league.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./league-details.component.ts"></script>
