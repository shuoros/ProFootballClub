<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.team.home.createOrEditLabel"
          data-cy="TeamCreateUpdateHeading"
          v-text="$t('proFootballClubApp.team.home.createOrEditLabel')"
        >
          Create or edit a Team
        </h2>
        <div>
          <div class="form-group" v-if="team.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="team.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.name')" for="team-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="team-name"
              data-cy="name"
              :class="{ valid: !$v.team.name.$invalid, invalid: $v.team.name.$invalid }"
              v-model="$v.team.name.$model"
              required
            />
            <div v-if="$v.team.name.$anyDirty && $v.team.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.homeland')" for="team-homeland">Homeland</label>
            <select
              class="form-control"
              name="homeland"
              :class="{ valid: !$v.team.homeland.$invalid, invalid: $v.team.homeland.$invalid }"
              v-model="$v.team.homeland.$model"
              id="team-homeland"
              data-cy="homeland"
              required
            >
              <option
                v-for="country in countryValues"
                :key="country"
                v-bind:value="country"
                v-bind:label="$t('proFootballClubApp.Country.' + country)"
              >
                {{ country }}
              </option>
            </select>
            <div v-if="$v.team.homeland.$anyDirty && $v.team.homeland.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.homeland.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.gender')" for="team-gender">Gender</label>
            <select
              class="form-control"
              name="gender"
              :class="{ valid: !$v.team.gender.$invalid, invalid: $v.team.gender.$invalid }"
              v-model="$v.team.gender.$model"
              id="team-gender"
              data-cy="gender"
              required
            >
              <option
                v-for="gender in genderValues"
                :key="gender"
                v-bind:value="gender"
                v-bind:label="$t('proFootballClubApp.Gender.' + gender)"
              >
                {{ gender }}
              </option>
            </select>
            <div v-if="$v.team.gender.$anyDirty && $v.team.gender.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.gender.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.money')" for="team-money">Money</label>
            <input
              type="number"
              class="form-control"
              name="money"
              id="team-money"
              data-cy="money"
              :class="{ valid: !$v.team.money.$invalid, invalid: $v.team.money.$invalid }"
              v-model.number="$v.team.money.$model"
              required
            />
            <div v-if="$v.team.money.$anyDirty && $v.team.money.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.money.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.money.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.points')" for="team-points">Points</label>
            <input
              type="number"
              class="form-control"
              name="points"
              id="team-points"
              data-cy="points"
              :class="{ valid: !$v.team.points.$invalid, invalid: $v.team.points.$invalid }"
              v-model.number="$v.team.points.$model"
              required
            />
            <div v-if="$v.team.points.$anyDirty && $v.team.points.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.points.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.points.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.matches')" for="team-matches">Matches</label>
            <input
              type="number"
              class="form-control"
              name="matches"
              id="team-matches"
              data-cy="matches"
              :class="{ valid: !$v.team.matches.$invalid, invalid: $v.team.matches.$invalid }"
              v-model.number="$v.team.matches.$model"
              required
            />
            <div v-if="$v.team.matches.$anyDirty && $v.team.matches.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.matches.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.matches.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.trophies')" for="team-trophies">Trophies</label>
            <input
              type="number"
              class="form-control"
              name="trophies"
              id="team-trophies"
              data-cy="trophies"
              :class="{ valid: !$v.team.trophies.$invalid, invalid: $v.team.trophies.$invalid }"
              v-model.number="$v.team.trophies.$model"
              required
            />
            <div v-if="$v.team.trophies.$anyDirty && $v.team.trophies.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.trophies.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.trophies.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.readiness')" for="team-readiness">Readiness</label>
            <input
              type="number"
              class="form-control"
              name="readiness"
              id="team-readiness"
              data-cy="readiness"
              :class="{ valid: !$v.team.readiness.$invalid, invalid: $v.team.readiness.$invalid }"
              v-model.number="$v.team.readiness.$model"
              required
            />
            <div v-if="$v.team.readiness.$anyDirty && $v.team.readiness.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.readiness.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.readiness.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.readiness.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.readiness.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.spirit')" for="team-spirit">Spirit</label>
            <input
              type="number"
              class="form-control"
              name="spirit"
              id="team-spirit"
              data-cy="spirit"
              :class="{ valid: !$v.team.spirit.$invalid, invalid: $v.team.spirit.$invalid }"
              v-model.number="$v.team.spirit.$model"
              required
            />
            <div v-if="$v.team.spirit.$anyDirty && $v.team.spirit.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.spirit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.spirit.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.spirit.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.spirit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.fans')" for="team-fans">Fans</label>
            <input
              type="number"
              class="form-control"
              name="fans"
              id="team-fans"
              data-cy="fans"
              :class="{ valid: !$v.team.fans.$invalid, invalid: $v.team.fans.$invalid }"
              v-model.number="$v.team.fans.$model"
              required
            />
            <div v-if="$v.team.fans.$anyDirty && $v.team.fans.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.fans.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.fans.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.fansSatisfaction')" for="team-fansSatisfaction"
              >Fans Satisfaction</label
            >
            <input
              type="number"
              class="form-control"
              name="fansSatisfaction"
              id="team-fansSatisfaction"
              data-cy="fansSatisfaction"
              :class="{ valid: !$v.team.fansSatisfaction.$invalid, invalid: $v.team.fansSatisfaction.$invalid }"
              v-model.number="$v.team.fansSatisfaction.$model"
              required
            />
            <div v-if="$v.team.fansSatisfaction.$anyDirty && $v.team.fansSatisfaction.$invalid">
              <small class="form-text text-danger" v-if="!$v.team.fansSatisfaction.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.fansSatisfaction.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.fansSatisfaction.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.team.fansSatisfaction.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.stadium')" for="team-stadium">Stadium</label>
            <select class="form-control" id="team-stadium" data-cy="stadium" name="stadium" v-model="team.stadium">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.stadium && stadiumOption.id === team.stadium.id ? team.stadium : stadiumOption"
                v-for="stadiumOption in stadiums"
                :key="stadiumOption.id"
              >
                {{ stadiumOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.coach')" for="team-coach">Coach</label>
            <select class="form-control" id="team-coach" data-cy="coach" name="coach" v-model="team.coach">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.coach && coachOption.id === team.coach.id ? team.coach : coachOption"
                v-for="coachOption in coaches"
                :key="coachOption.id"
              >
                {{ coachOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.team.team')" for="team-team">Team</label>
            <select class="form-control" id="team-team" data-cy="team" name="team" v-model="team.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="team.team && teamOption.id === team.team.id ? team.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.team.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-update.component.ts"></script>
