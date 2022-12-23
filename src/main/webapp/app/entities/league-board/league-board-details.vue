<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="leagueBoard">
        <h2 class="jh-entity-heading" data-cy="leagueBoardDetailsHeading">
          <span v-text="$t('proFootballClubApp.leagueBoard.detail.title')">LeagueBoard</span> {{ leagueBoard.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.position')">Position</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.position }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.win')">Win</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.win }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.lose')">Lose</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.lose }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.draw')">Draw</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.draw }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.goalDifference')">Goal Difference</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.goalDifference }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.points')">Points</span>
          </dt>
          <dd>
            <span>{{ leagueBoard.points }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.team')">Team</span>
          </dt>
          <dd>
            <div v-if="leagueBoard.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: leagueBoard.team.id } }">{{ leagueBoard.team.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.leagueBoard.league')">League</span>
          </dt>
          <dd>
            <div v-if="leagueBoard.league">
              <router-link :to="{ name: 'LeagueView', params: { leagueId: leagueBoard.league.id } }">{{
                leagueBoard.league.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="leagueBoard.id"
          :to="{ name: 'LeagueBoardEdit', params: { leagueBoardId: leagueBoard.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./league-board-details.component.ts"></script>
