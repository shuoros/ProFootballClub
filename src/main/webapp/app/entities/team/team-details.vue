<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="team">
        <h2 class="jh-entity-heading" data-cy="teamDetailsHeading">
          <span v-text="$t('proFootballClubApp.team.detail.title')">Team</span> {{ team.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proFootballClubApp.team.name')">Name</span>
          </dt>
          <dd>
            <span>{{ team.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.homeland')">Homeland</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Country.' + team.homeland)">{{ team.homeland }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.gender')">Gender</span>
          </dt>
          <dd>
            <span v-text="$t('proFootballClubApp.Gender.' + team.gender)">{{ team.gender }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.money')">Money</span>
          </dt>
          <dd>
            <span>{{ team.money }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.points')">Points</span>
          </dt>
          <dd>
            <span>{{ team.points }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.matches')">Matches</span>
          </dt>
          <dd>
            <span>{{ team.matches }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.trophies')">Trophies</span>
          </dt>
          <dd>
            <span>{{ team.trophies }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.readiness')">Readiness</span>
          </dt>
          <dd>
            <span>{{ team.readiness }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.spirit')">Spirit</span>
          </dt>
          <dd>
            <span>{{ team.spirit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.fans')">Fans</span>
          </dt>
          <dd>
            <span>{{ team.fans }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.fansSatisfaction')">Fans Satisfaction</span>
          </dt>
          <dd>
            <span>{{ team.fansSatisfaction }}</span>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.stadium')">Stadium</span>
          </dt>
          <dd>
            <div v-if="team.stadium">
              <router-link :to="{ name: 'StadiumView', params: { stadiumId: team.stadium.id } }">{{ team.stadium.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.coach')">Coach</span>
          </dt>
          <dd>
            <div v-if="team.coach">
              <router-link :to="{ name: 'CoachView', params: { coachId: team.coach.id } }">{{ team.coach.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proFootballClubApp.team.team')">Team</span>
          </dt>
          <dd>
            <div v-if="team.team">
              <router-link :to="{ name: 'TeamView', params: { teamId: team.team.id } }">{{ team.team.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="team.id" :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-details.component.ts"></script>
